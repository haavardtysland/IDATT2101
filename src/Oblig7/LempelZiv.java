package Oblig7;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;


public class LempelZiv {

  private StringBuffer buffer;

  public LempelZiv() {
    this.buffer = new StringBuffer(Short.MAX_VALUE);
  }

  private void trimSearchBuffer() {
    if (buffer.length() > Short.MAX_VALUE) {
      buffer = buffer.delete(0, buffer.length() - Short.MAX_VALUE);
    }
  }

  public void compress(File file, String outputPath) {
    try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
         DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(outputPath))))) {
      byte[] array = new byte[dis.available()];
      dis.readFully(array);
      String current = "";
      String beforeMatch = "";
      int matchIndex = 0;
      int temp;

      for (int i = 0; i < array.length; i++) {
        current += (char) array[i];
        temp = buffer.indexOf(current);
        if (temp != -1) { // MATCH
          while (temp != -1) {
            i++;
            if (current.length() == Byte.MAX_VALUE || i == array.length) {
              break;
            }
            matchIndex = temp;
            current += (char) array[i];
            temp = buffer.indexOf(current);
            if (temp == -1) {
              current = current.substring(0, current.length() - 1);
              i--;
            }
          }
          if ((current.length()) > 3) {
            if (beforeMatch.length() > 0) {
              dos.write((beforeMatch.length() * (-1)));
              dos.writeBytes(beforeMatch);
            }
            dos.write((current.length()));
            short s = (short) (buffer.length() - matchIndex);
            byte b1 = (byte) (s & 0xff);
            byte b2 = (byte) ((s >> 8) & 0xff);
            dos.writeShort((buffer.length() - matchIndex));
            buffer.append(current);
            current = "";
            beforeMatch = "";
          }
        }
        if (beforeMatch.length() == Byte.MAX_VALUE) {
          dos.write((beforeMatch.length() * (-1)));
          dos.writeBytes(beforeMatch);
          beforeMatch = "";
        }

        if (temp == -1 && current.length() > 0) {
          beforeMatch += current.charAt(0);
          buffer.append(current.charAt(0));
          current = current.substring(1, current.length());
        }
        // Sjekker om komprimert streng er kortere enn representativ streng.
        trimSearchBuffer();
      }
      if (beforeMatch.length() != 0) {
        dos.write((beforeMatch.length() + current.length()) * (-1));
        dos.writeBytes(beforeMatch + current);
      }

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  public void decompress(File file, String outputPath) {
    try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
         DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(outputPath))))) {
      byte[] array = new byte[dis.available()];
      dis.readFully(array);

      List<Byte> result = new ArrayList<>();
      int index = 0;
      int count = 0;
      byte current;
      short numberBack;

      for (int i = 0; i < array.length; i++) {
        current = array[i];

        if (current < 0) {
          for (int j = i + 1; j <= (Math.abs(current) + i); j++) {
            result.add(array[j]);
            count++;
            index = j;
          }
          i = index;
        }

        if (current > 0) {
          byte b1 = array[i + 1];
          byte b2 = array[i + 2];
          numberBack = (short) ((b1 & 0xff) << 8 | b2 & 0xff);
          int interval = ((count - numberBack) + Math.abs(current));
          for (int j = count - numberBack; j < interval; j++) {
            result.add(result.get(j));
            count++;
          }
          i += 2;
        }
      }
      byte[] output = new byte[result.size()];
      for (int i = 0; i < result.size(); i++) {
        output[i] = result.get(i);
      }
      dos.write(output);

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException io) {
      io.printStackTrace();
    }

  }

  public static void main(String[] args) {
    LempelZiv lempelZiv = new LempelZiv();
    File uncompressed = new File("test.txt");
    lempelZiv.compress(uncompressed, "C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\ferdigtest.txt");
    File compressed = new File("ferdigtest.txt");
    lempelZiv.decompress(compressed,"C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\dekomprimert.txt");
  }

}

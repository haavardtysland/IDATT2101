//package Oblig7;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LempelZivDecompression {

  private StringBuffer buffer;

  public LempelZivDecompression() {
    this.buffer = new StringBuffer(Short.MAX_VALUE);
  }

  private void trimSearchBuffer() {
    if (buffer.length() > Short.MAX_VALUE) {
      buffer = buffer.delete(0, buffer.length() - Short.MAX_VALUE);
    }
  }

  public void decompress(File file, String outputPath) {
    try (
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
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
}

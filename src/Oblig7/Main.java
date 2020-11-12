//package Oblig7;

import java.io.File;
import java.io.IOException;

public class Main {

  static LempelZivCompression lempelZiv = new LempelZivCompression();
  static LempelZivDecompression lempelZivDecompression= new LempelZivDecompression();
  static Huffman huffman = new Huffman();


  public static void main(String[] args) throws IOException {
    //Her må path endres til brukerens path.
    kompress("test.txt","C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\lzkomprimert.txt", "C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\huffkomprimert.txt");
    dekompress("C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\huffkomprimert.txt", "C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\huffdekomprimert.txt", "C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\ferdig.txt");

  }

  //Først en path til en eller annen txt fil, deretter en outputpath der den lempelZiv komprimerte filen skal legges (MED FILNAVN), og til slutt hvor den huffman komprimerte filen skal legges (MED FILNAVN)
  public static void kompress(String path, String lzOutputPath, String huffOutputPath) throws IOException {
    File uncompressed = new File(path);
    lempelZiv.compress(uncompressed, lzOutputPath);
    huffman.writeCompressedFile(lzOutputPath, huffOutputPath);


  }

  //Først path til den komprimerte filen, deretter en path der den dekomprimerte huffman filen skal legges (MED FILNAVN), og til slutt hvor den lempelziv dekomprimerte filen(den ferdige) skal legges (MED FILNAVN)
  public static void dekompress(String path, String huffOutputPath, String lzOutputPath) throws IOException{
    huffman.decodeCompressedFiled(path, huffOutputPath);
    File uncompressedHuff = new File(huffOutputPath);
    lempelZivDecompression.decompress(uncompressedHuff,lzOutputPath);


  }
}

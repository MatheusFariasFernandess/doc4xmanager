package org.example;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.P;
import org.docx4j.wml.R;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws Exception {
        File arquivo = new File("test.docx");
        byte[]imagem = Files.readAllBytes(new File("C:\\Users\\d2ti-\\Documents\\learn\\untitled\\yaghfloerlnb1.png").toPath());
        WordprocessingMLPackage wordprocessingMLPackage = WordprocessingMLPackage.createPackage();

        MainDocumentPart mainDocumentPart = wordprocessingMLPackage.getMainDocumentPart();

        BinaryPartAbstractImage binaryPartAbstractImage
                = BinaryPartAbstractImage.createImagePart(wordprocessingMLPackage,imagem);

        Inline imageInline = binaryPartAbstractImage.createImageInline("", "", 1, 1);
        Drawing drawing = new Drawing();
        drawing.getAnchorOrInline().add(imageInline);

        P paragraph = new P();
        R run = new R();

        paragraph.getContent().add(run);
        run.getContent().add(drawing);

        mainDocumentPart.getContent().add(paragraph);
        wordprocessingMLPackage.save(arquivo);
    }
}
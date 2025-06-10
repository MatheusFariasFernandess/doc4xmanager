package org.example;

import org.docx4j.TextUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("teste.docx");

        WordprocessingMLPackage wordprocessingMLPackage =
                WordprocessingMLPackage.load(new FileInputStream(file.getPath()));

        MainDocumentPart documentPart = wordprocessingMLPackage.getMainDocumentPart();

        List<Object> allElementFromObject = getAllElementFromObject(wordprocessingMLPackage.getMainDocumentPart(), Text.class);
        for (Object texto : allElementFromObject) {
            Text text = (Text) texto;
            if (text.getValue().equals("#MATHEUS")) {
                text.setValue("SUBSTITUIDO");
            }
        }
        File file1 = new File("targe.docx");
        wordprocessingMLPackage.save(file1);
    }

    public static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<>();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }

        if (obj.getClass().equals(toSearch)) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for(Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }

    public static MainDocumentPart readFile(String path) throws Exception {
        WordprocessingMLPackage wordprocessingMLPackage =
                WordprocessingMLPackage.load(new FileInputStream(path));

        return wordprocessingMLPackage.getMainDocumentPart();
    }

    public static void addTable(WordprocessingMLPackage wordprocessingMLPackage, String texto) {
        Tbl tbl = TblFactory.createTable(3, 3, 1300);
        List<Object> rows = tbl.getContent();
        for (Object row : rows) {
            P paragraph = new P();
            Tr tr = (Tr) row;
            for (Object cell : tr.getContent()) {
                Tc tc = (Tc) cell;
                Text text = new Text();
                text.setValue(texto);
                R r = new R();
                r.getContent().add(text);
                paragraph.getContent().add(r);
                tc.getContent().add(paragraph);
            }
        }

        wordprocessingMLPackage.getMainDocumentPart().getContent().add(tbl);
    }

    public static void addImage(WordprocessingMLPackage wordMLPackage, List<String> imagems) throws Exception {
        MainDocumentPart mainDocumentPart
                = wordMLPackage.getMainDocumentPart();
        for (String imagem : imagems) {
            File imageFile = new File(imagem);
            BinaryPartAbstractImage binaryPartAbstractImage
                    = BinaryPartAbstractImage.createImagePart(wordMLPackage, imageFile);

            Inline imageInline
                    = binaryPartAbstractImage.createImageInline("", "", 0, 1);
            Drawing drawing = new Drawing();
            drawing.getAnchorOrInline().add(imageInline);
            R run = new R();
            run.getContent().add(drawing);

            P paragraph = new P();
            paragraph.getContent().add(run);

            mainDocumentPart.getContent().add(paragraph);
        }
    }
}
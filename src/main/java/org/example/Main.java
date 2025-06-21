package org.example;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.example.design.chain.ConnectDataBase;
import org.example.design.chain.Hande;
import org.example.design.chain.RightUserName;
import org.example.design.chain.UserNameAndPasswordNotBlank;

import javax.xml.bind.JAXBElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        Chain();

    }


    public static void Chain() {
        Hande chain = new UserNameAndPasswordNotBlank();
        chain.setNext(new RightUserName())
                .setNext(new ConnectDataBase());


        // Teste com username errado
        chain.handle("MATHEUS", "123");
    }

    public static void escreverDados() {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("text.txt"))) {
            bufferedOutputStream.write(new byte[]{65, 66, 67});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Object> getElement(Object obj, Class<?> classBind) {
        List<Object> result = new ArrayList<>();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }

        if (obj.getClass().equals(classBind)) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> childs = ((ContentAccessor) obj).getContent();
            for (Object child : childs) {
                result.addAll(getElement(child, classBind));
            }
        }
        return result;
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
            for (Object child : children) {
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
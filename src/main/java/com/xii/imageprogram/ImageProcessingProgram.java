package com.xii.imageprogram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class ImageProcessingProgram extends JFrame {
    
	//출처 : https://blog.naver.com/qbxlvnf11/221091939148
	
    static int size =256;
    
    // 입력과 출력 배열을 원본 사진과 동일한 256 X 256 크기로 설정합니다.
    // 각 이미지의 픽셀은 0~255의 값을 가지므로 데이터 형식을 int로 합니다.
    static int[][] inImage = new int[size][size];
    static int[][] outImage = new int[size][size];
 
    static Container contentPane; // 윈도우 창을 출력할 컨테이너
 
    public static void main(String[] args) throws Exception {
        new ImageProcessingProgram();
    }
 
    ImageProcessingProgram() { // Constructor
 
        // title
        setTitle("사진 처리 프로그램");
        // 종료 버튼을 누르면 프로그램 종료
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        contentPane = getContentPane();
 
        // 메뉴 추가
        makeMenu();
 
        // 패널 추가
        DrawImage panel = new DrawImage();
        contentPane.add(panel, BorderLayout.CENTER);
 
        // 윈도 창의 메뉴나 틀의 폭까지 고려해서 크기 조절
        setSize(8 + size + 8, 25 + 31 + size + 8);
 
        // 윈도 창 보기
        setVisible(true);
 
        // 윈도 창 새로 고침
        displayImage();
    }
 
    void makeMenu() {
        // 메뉴바 생성
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
 
        // 메뉴 생성
        JMenu menu = new JMenu("파일");
        JMenuItem loadAction = new JMenuItem("불러오기");
        JMenuItem saveAction = new JMenuItem("저장");
        JMenuItem exitAction = new JMenuItem("종료");
        menu.add(loadAction);
        menu.add(saveAction);
        menu.add(exitAction);
 
        // 메뉴2 생성
        JMenu menu2 = new JMenu("사진 처리");
        JMenuItem equalAction = new JMenuItem("원본");
        JMenuItem negativeAction = new JMenuItem("반전시키기");
        JMenuItem mirrorAction = new JMenuItem("좌우 대칭");
        JMenuItem mirror2Action = new JMenuItem("상하 대칭");
        menu2.add(equalAction);
        menu2.add(negativeAction);
        menu2.add(mirrorAction);
        menu2.add(mirror2Action);
 
        // 추가
        menuBar.add(menu);
        menuBar.add(menu2);
 
        // 이벤트 걸기
        loadAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                load();
            }
        });
        saveAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                save();
            }
        });
        exitAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        equalAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                equal();
            }
        });
        negativeAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                negative();
            }
        });
        mirrorAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mirror();
            }
        });
        mirror2Action.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mirror2();
            }
        });
    }
 
    void load(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("파일 불러오기");
        fileChooser.setFileFilter(new FileNameExtensionFilter("RAW File", "raw")); // 파일필터
        fileChooser.setMultiSelectionEnabled(false);// 다중 선택 불가
        int returnVal = fileChooser.showOpenDialog(this); // show openDialog 
        if (returnVal == JFileChooser.APPROVE_OPTION) { // 파일을 선택하였을 때
            try{
                loadImage(fileChooser.getSelectedFile().toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
 
    static void loadImage(String path) throws Exception {
        // 이미지
        File inFile;
        inFile = new File(path);
 
        // 파일 스트림
        FileInputStream inFileStream;
        inFileStream = new FileInputStream(inFile.getPath());
 
        // 읽어온 이미지 배열에 저장
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                inImage[i][k] = inFileStream.read();
                outImage[i][k] = inImage[i][k];
            }
        }
 
        // close
        inFileStream.close();
 
        displayImage();
    }
 
    void save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("파일 저장");
        fileChooser.setFileFilter(new FileNameExtensionFilter("RAW File", "raw")); // 파일필터
        fileChooser.setMultiSelectionEnabled(false); // 다중 선택 불가
        int returnVal = fileChooser.showSaveDialog(this); // show saveDialog 
        if (returnVal == JFileChooser.APPROVE_OPTION) { // 파일을 선택하였을 때
            try {
                saveImage(fileChooser.getSelectedFile().toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    static void saveImage(String path) throws Exception {
        // 이미지
        File outFile;
        outFile = new File(path);
 
        // 파일 스트림
        FileOutputStream outFileStream;
        outFileStream = new FileOutputStream(outFile.getPath()+".raw");
 
        // 이미지 파일로 저장
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                outFileStream.write(outImage[i][k]);
            }
        }
 
        // close
        outFileStream.close();
        
        // messageDialog
        JOptionPane.showMessageDialog(null, "파일 저장 성공", "파일 저장", JOptionPane.INFORMATION_MESSAGE);
    }
 
 
    void equal() {
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                outImage[i][k] = inImage[i][k];
            }
        }
 
        displayImage();
    }
 
    void negative() {
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                outImage[i][k] = (size - 1) - inImage[i][k];
            }
        }
 
        displayImage();
    }
 
    void mirror() {
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                outImage[i][k] = inImage[i][(size - 1) - k];
            }
        }
 
        displayImage();
    }
 
    void mirror2() {
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                outImage[i][k] = inImage[(size - 1) - i][k];
            }
        }
 
        displayImage();
    }
 
    class DrawImage extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int R, G, B;
 
            // 픽셀 하나하나를 화면에 출력
            for (int i = 0; i < size; i++) {
                for (int k = 0; k < size; k++) {
                    R = G = B = (int) outImage[i][k];
 
                    g.setColor(new Color(R, G, B)); // 색깔 설정(R, G, B가 같은 색: 그레이색상)
                    g.drawRect(k, i, 1, 1); // 사각형 그리는 함수 => 픽셀 출력(x좌표, y좌표, 가로, 세로)
                }
            }
        }
    }
 
    static void displayImage() {
        // outImage의 내용이 변경될 때마다 이 메소드를 호출하여 화면에 변경된 내용을 출력함
        Graphics g = contentPane.getGraphics();
        contentPane.paintAll(g);
    }
}
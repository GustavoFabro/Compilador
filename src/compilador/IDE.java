package compilador;


/**
 * Compilador - Linguagem C-Basic
 * @
 *
 * Disciplina: Compiladores
 * Professores: Christine Vieira
 * Acadêmico: Gustavo Fabro
 */

import util.TableTokensModel;
import util.TextLineNumber;
import util.Button;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author gustavo
 */
public class IDE extends JFrame implements ActionListener, DocumentListener {

    private String textoArquivo;
    private String nomeArquivo;

    private final JFileChooser fc = new JFileChooser();
    private final JTextPane codeArea;
    private final JScrollPane jScrollTokens;
    private final JScrollPane js;

    private final JPanel panelTokens;
    private final JPanel barra;

    private final JTable tokensTable;
    private static TableTokensModel tableTokensModel;

    private final JButton btnCompile;
    private final JButton btnNewFile;
    private final JButton btnSave;
    // private final JButton btnSaveAs;
    private final JButton btnOpen;

    private final JMenuBar mb;

    private final JMenuItem salvar;
    // private final JMenuItem salvarComo;
    private final JMenuItem sobre;
    private final JMenuItem abrir;
    private final JMenuItem novo;
    private final JMenuItem sair;
    private final JMenuItem olaMundo;
    private final JMenuItem function;

    private final JMenu subMenuEx;
    private final JMenu menuArquivo;
    private final JMenu menuSobre;

    private final JTextArea textAreaPilha;
    private final JScrollPane scrollPane;

    private final AnalisadorLexico analisadorLexico;

    Container c;

    public IDE() {
        super("C Basic Editor");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/layout/new_file.png"));
        this.setIconImage(iconeTitulo);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        analisadorLexico = new AnalisadorLexico(this);

        c = getContentPane();
        c.setLayout(new BorderLayout());

        /* Menus */
        //barra de menus
        mb = new JMenuBar();

        //Item Arquivo
        menuArquivo = new JMenu("Arquivo");

        novo = new JMenuItem("Novo Arquivo");
        novo.setIcon(new ImageIcon(
                new ImageIcon((getClass().getResource("/layout/new_file.png")))
                        .getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));

        abrir = new JMenuItem("Abrir Arquivo");
        abrir.setIcon(new ImageIcon(
                new ImageIcon((getClass().getResource("/layout/open_file.png")))
                        .getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));

        salvar = new JMenuItem("Salvar");
        salvar.setIcon(new ImageIcon(
                new ImageIcon((getClass().getResource("/layout/save_file.png")))
                        .getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
        salvar.setEnabled(false);

       /* salvarComo = new JMenuItem("Salvar Como...");
        salvarComo.setIcon(new ImageIcon(
                new ImageIcon((getClass().getResource("/layout/save_file.png")))
                .getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
        salvarComo.setEnabled(false);*/


        sair = new JMenuItem("Sair");

        //Submenu de exemplos e seus itens
        subMenuEx = new JMenu("Exemplos");

        olaMundo = new JMenuItem("Hello, World!");
        function = new JMenuItem("Função");

        subMenuEx.add(olaMundo);
        subMenuEx.add(function);

        menuArquivo.add(novo);
        menuArquivo.add(abrir);
        menuArquivo.add(salvar);
        menuArquivo.add(subMenuEx);
        menuArquivo.addSeparator();
        menuArquivo.add(sair);

        //Item Sobre
        menuSobre = new JMenu("Ajuda");
        sobre = new JMenuItem("Sobre");

        menuSobre.add(sobre);

        mb.add(menuArquivo);
        mb.add(menuSobre);

        this.setJMenuBar(mb);

        //Listeners e actionCommands
        novo.addActionListener(this);
        abrir.addActionListener(this);
        salvar.addActionListener(this);
        //salvarComo.addActionListener(this);
        olaMundo.addActionListener(this);
        function.addActionListener(this);

        sair.addActionListener(this);
        sobre.addActionListener(this);

        novo.setActionCommand("novo");
        abrir.setActionCommand("abrir");
        salvar.setActionCommand("salvar");
        // salvarComo.setActionCommand("salvarComo");


        olaMundo.setActionCommand("exemplo");
        function.setActionCommand("exemplo");
        sair.setActionCommand("sair");
        sobre.setActionCommand("sobre");
        menuArquivo.setActionCommand("menuArq");

        /* Editor */
        codeArea = new JTextPane();
        codeArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        codeArea.setEnabled(false);
        codeArea.setBackground(Color.LIGHT_GRAY);
        codeArea.getDocument().addDocumentListener(this);

        js = new JScrollPane(codeArea);
        js.setBounds(0, 0, 800, 600);
        TextLineNumber tln = new TextLineNumber(codeArea);
        js.setRowHeaderView(tln);

        /* Botões */
        btnCompile = new Button("/layout/play.png", "btnCompile", this);
        btnCompile.setEnabled(false);
        btnNewFile = new Button("/layout/new_file.png", "novo", this);
        btnSave = new Button("/layout/save_file.png", "salvar", this);
        btnSave.setEnabled(false);
        // btnSaveAs = new Button("/layout/save_file.png", "salvarComo", this);
        //  btnSaveAs.setEnabled(false);
        btnOpen = new Button("/layout/open_file.png", "abrir", this);

        /* Barra */
        barra = new JPanel();
        barra.setLayout(new BoxLayout(barra, BoxLayout.LINE_AXIS));
        barra.setPreferredSize(new Dimension(800, 30));
        barra.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        barra.add(btnOpen);
        barra.add(Box.createRigidArea(new Dimension(10, 0)));
        barra.add(btnNewFile);
        barra.add(Box.createRigidArea(new Dimension(10, 0)));
        barra.add(btnSave);
        barra.add(Box.createRigidArea(new Dimension(10, 0)));
        barra.add(btnCompile);

        /* Tokens */
        panelTokens = new JPanel();
        panelTokens.setPreferredSize(new Dimension(210, 800));
        panelTokens.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        tableTokensModel = new TableTokensModel();

        tokensTable = new JTable();
        tokensTable.setModel(tableTokensModel);
        tokensTable.setPreferredScrollableViewportSize(new Dimension(200, 650));

        /*Pilha Debug*/
        textAreaPilha = new JTextArea();
        textAreaPilha.setEditable(false);
        scrollPane = new JScrollPane(textAreaPilha);
        scrollPane.setPreferredSize (new Dimension(500, 300) );

        //auto ajuste na altura da tabela
        tokensTable.setFillsViewportHeight(true);

        jScrollTokens = new JScrollPane(tokensTable);

        panelTokens.add(jScrollTokens);

        /* Adiconando itens ao Frame */
        c.add(BorderLayout.WEST, panelTokens);
        c.add(BorderLayout.CENTER, js);
        c.add(BorderLayout.NORTH, barra);

        setIcon();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (ae.getActionCommand()) {
            case "novo":
                novoArquivo();
                break;

            case "abrir":
                if (salvarMudancas()) {
                    abrirDialogo();
                }
                break;

            case "salvarComo":

                break;
            case "salvar":
                salvarComoDialogo();

                break;
            case "exemplo":
                setCodeAreaText("/exemplos/" + ((JMenuItem) ae.getSource()).getText() + ".cb",
                        "Exemplo " + ((JMenuItem) ae.getSource()).getText(), true);

                break;
            case "sair":
                if (salvarMudancas()) {
                    System.exit(0);
                }
                break;

            case "btnCompile":
                tableTokensModel.clearTable();

                analisadorLexico.analisar(codeArea.getText());

                break;
            case "sobre":
                JOptionPane.showMessageDialog(this, "Trabalho 6º Semestre "
                        + "disciplina Compiladores ", "Sobre", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    public void setTokensTable(ArrayList<Token> tokens) {
        tableTokensModel.clearTable();
        for (int i = 0; i < tokens.size(); i++) {
            tableTokensModel.addRow(tokens.get(i));
        }
    }

    public void showPilhaDebug(String pilhaDebug) {
        textAreaPilha.setText(pilhaDebug.trim());

        JOptionPane.showMessageDialog(this, scrollPane,
                "Pilha", JOptionPane.INFORMATION_MESSAGE);
    }

    public void novoArquivo() {
        if (salvarMudancas()) {
            codeArea.setText("");
            nomeArquivo = "untitled.cb";
            setTitleFrame(nomeArquivo);
            enableCodeArea();
            enableSave(true);
            tableTokensModel.clearTable();
        }
    }

    public void salvarComoDialogo() {
        //Exibe o diálogo. Deve ser passado por parâmetro o JFrame de origem.
        if (fc.showSaveDialog(c) != JFileChooser.CANCEL_OPTION) {
            //Captura o objeto File que representa o arquivo selecionado.
            File selFile = fc.getSelectedFile();

            if (selFile != null) {
                nomeArquivo = fc.getSelectedFile().getName();
                gravarArquivo(fc.getSelectedFile().getAbsolutePath(), codeArea.getText());
            }
        }
    }

    public void salvar(){
        if(fc != null){
            gravarArquivo(fc.getSelectedFile().getAbsolutePath(), codeArea.getText());
        }else
            salvarComoDialogo();

    }
    public void abrirDialogo() {
        //Exibe o diálogo. Deve ser passado por parâmetro o JFrame de origem.
        if (fc.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
            //Captura o objeto File que representa o arquivo selecionado.
            File selFile = fc.getSelectedFile();

            if (selFile != null) {
                setCodeAreaText(selFile.getAbsolutePath(), selFile.getName(), false);
            }
        }
    }

    private String lerArquivo(String path, boolean isLocal) {
        BufferedReader bufferedReader = null;

        try {
            if (isLocal) {
                bufferedReader
                        = new BufferedReader(
                        new InputStreamReader(getClass().getResourceAsStream(path)));
            } else {
                bufferedReader = new BufferedReader(new FileReader(path));
            }

            StringBuilder sb = new StringBuilder();
            while (bufferedReader.ready()) {
                sb.append(bufferedReader.readLine());
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo: "
                    + ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo: "
                            + ex.getMessage());
                }
            }
        }
        return null;
    }

    private void gravarArquivo(String path, String textoArquivo) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(path, false);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(textoArquivo);
            bufferedWriter.flush();
            setTitleFrame(nomeArquivo);

            //Se chegou ate essa linha, conseguiu salvar o arquivo com sucesso.
            JOptionPane.showMessageDialog(this, "Salvo com sucesso");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + ex.getMessage());
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: "
                            + ex.getMessage());
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: "
                            + ex.getMessage());
                }
            }
        }
    }

    public boolean salvarMudancas() {
        if (!codeArea.getText().equals(textoArquivo) && codeArea.isEnabled()) {
            int opt = JOptionPane.showConfirmDialog(null,
                    "Salvar modificações em \"" + nomeArquivo + "\"?");

            if (opt == JOptionPane.YES_OPTION) {
                salvarComoDialogo();
            } else if (opt == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }

        return true;
    }

    public void setCodeAreaText(String path, String nome, boolean isLocal) {
        textoArquivo = lerArquivo(path, isLocal);
        nomeArquivo = nome;
        codeArea.setText(textoArquivo);
        setTitleFrame(nomeArquivo);
        enableCodeArea();
        enableSave(true);
    }

    public void enableCodeArea() {
        codeArea.setBackground(Color.WHITE);
        codeArea.setEnabled(true);
        btnCompile.setEnabled(true);
    }

    public void setStateError() {
        codeArea.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }

    public void enableSave(boolean estado) {
        /*  if (codeArea.isEnabled()
                && !codeArea.getText().equals(textoArquivo)) {
            btnSave.setEnabled(true);
            salvar.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
            salvar.setEnabled(false);
        }*/
        btnSave.setEnabled(estado);
        salvar.setEnabled(estado);
    }

    public void setTitleFrame(String title) {
        this.setTitle(nomeArquivo + " - C Basic Editor");
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        // enableSave(true);
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("/layout/new_file.png"));
    }
}
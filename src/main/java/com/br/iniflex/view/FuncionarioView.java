package com.br.iniflex.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import com.br.iniflex.models.Funcionario;

@Component
public class FuncionarioView {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat numberFormatter = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
    private JFrame frame;
    private JTable table;
    private JTextArea messageArea;

    private final Color originalMessageAreaColor;
    private final Color highlightColor = new Color(255, 255, 204);

    private final JButton btnImprimir;
    private final JButton btnRemoverJoao;
    private final JButton btnAumento10;
    private final JButton btnAgrupar;
    private final JButton btnAniversariantes;
    private final JButton btnMaisVelho;
    private final JButton btnAlfabetica;
    private final JButton btnTotalSalarios;
    private final JButton btnSalariosMinimos;

    public FuncionarioView() {
        this.numberFormatter.setMinimumFractionDigits(2);
        this.numberFormatter.setMaximumFractionDigits(2);

        frame = new JFrame("Iniflex - Gestão de Funcionários");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        btnImprimir = new JButton("Imprimir Todos");
        btnRemoverJoao = new JButton("Remover João");
        btnAumento10 = new JButton("Aumento de 10%");
        btnAgrupar = new JButton("Agrupar por Função");
        btnAniversariantes = new JButton("Aniversariantes (Out/Dez)");
        btnMaisVelho = new JButton("Funcionário Mais Velho");
        btnAlfabetica = new JButton("Ordem Alfabética");
        btnTotalSalarios = new JButton("Total de Salários");
        btnSalariosMinimos = new JButton("Salários Mínimos");

        buttonPanel.add(btnImprimir);
        buttonPanel.add(btnRemoverJoao);
        buttonPanel.add(btnAumento10);
        buttonPanel.add(btnAgrupar);
        buttonPanel.add(btnAniversariantes);
        buttonPanel.add(btnMaisVelho);
        buttonPanel.add(btnAlfabetica);
        buttonPanel.add(btnTotalSalarios);
        buttonPanel.add(btnSalariosMinimos);

        String[] columnNames = {"Nome", "Data de Nascimento", "Salário", "Função"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        messageArea = new JTextArea(5, 30);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        originalMessageAreaColor = messageArea.getBackground();

        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setBorder(new TitledBorder("Mensagens"));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(messageScrollPane, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    public void setControllerActionListener(ActionListener listener) {
        btnImprimir.addActionListener(listener);
        btnRemoverJoao.addActionListener(listener);
        btnAumento10.addActionListener(listener);
        btnAgrupar.addActionListener(listener);
        btnAniversariantes.addActionListener(listener);
        btnMaisVelho.addActionListener(listener);
        btnAlfabetica.addActionListener(listener);
        btnTotalSalarios.addActionListener(listener);
        btnSalariosMinimos.addActionListener(listener);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    // salvar isso daqui pra eu usar em outros projtos que usam swing
    public void displayMessage(String title, String message) {
        SwingUtilities.invokeLater(() -> {
            messageArea.setText("▶ " + title + ":\n" + message);
            messageArea.setBackground(highlightColor);

            Timer timer = new Timer(500, e -> {
                messageArea.setBackground(originalMessageAreaColor);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    public void clearTable() {
        SwingUtilities.invokeLater(() -> ((DefaultTableModel) table.getModel()).setRowCount(0));
    }

    public void exibirFuncionarios(List<Funcionario> funcionarios) {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (Funcionario f : funcionarios) {
                String salarioFormatado = numberFormatter.format(f.getSalario());
                Object[] rowData = {
                    f.getNome(),
                    f.getDataNascimento().format(dateFormatter),
                    salarioFormatado,
                    f.getFuncao()
                };
                model.addRow(rowData);
            }
        });
    }

    public String formatarBigDecimal(BigDecimal valor) {
        return numberFormatter.format(valor);
    }
}

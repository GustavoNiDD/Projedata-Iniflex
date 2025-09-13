package com.br.iniflex.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import com.br.iniflex.models.Funcionario;
import com.br.iniflex.service.FuncionarioService;
import com.br.iniflex.view.FuncionarioView;

@Controller
public class FuncionarioController implements ActionListener {

    private final FuncionarioService service;
    private final FuncionarioView view;

    public FuncionarioController(FuncionarioService service, FuncionarioView view) {
        this.service = service;
        this.view = view;
        this.view.setControllerActionListener(this);
    }

    public void start() {
        view.show();

        view.displayMessage("Bem-vindo", "Sistema de Gestão de Funcionários.\nSelecione uma das opções ao lado para começar.");
    }

    public void adicionarFuncionarios(List<Funcionario> funcionarios) {
        service.inserirFuncionarios(funcionarios);
    }

    public void removerFuncionario(String nome) {
        service.removerFuncionario(nome);
        view.displayMessage("Remoção", "Funcionário 'João' removido com sucesso.");
    }

    public void imprimirTodosFuncionarios() {
        List<Funcionario> funcionarios = service.getTodosFuncionarios();
        view.exibirFuncionarios(funcionarios);
    }

    public void aplicarAumento(BigDecimal percentual) {
        service.darAumento(percentual);
        view.displayMessage("Aumento", "Aumento de 10% aplicado com sucesso a todos os funcionários!");
    }

    public void imprimirFuncionariosAgrupadosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosAgrupados = service.agruparPorFuncao();

        String resultado = funcionariosAgrupados.entrySet().stream()
                .map(entry -> {
                    String funcao = entry.getKey();
                    String nomes = entry.getValue().stream()
                            .map(Funcionario::getNome)
                            .collect(Collectors.joining(", "));
                    return "▶ " + funcao + ": " + nomes;
                })
                .collect(Collectors.joining("\n"));

        List<Funcionario> funcionariosOrdenados = service.getTodosFuncionarios().stream()
                .sorted(Comparator.comparing(Funcionario::getFuncao).thenComparing(Funcionario::getNome))
                .collect(Collectors.toList());

        view.exibirFuncionarios(funcionariosOrdenados);
        view.displayMessage("Funcionários Agrupados por Função", resultado);
    }

    public void imprimirAniversariantes(int... meses) {
        List<Funcionario> aniversariantes = service.getAniversariantesPorMes(meses);
        view.exibirFuncionarios(aniversariantes);
        view.displayMessage("Aniversariantes", "Exibindo funcionários que fazem aniversário nos meses 10 e 12.");
    }

    public void imprimirFuncionarioMaisVelho() {
        Funcionario maisVelho = service.getFuncionarioMaiorIdade();
        if (maisVelho != null) {
            view.exibirFuncionarios(List.of(maisVelho));
            view.displayMessage("Funcionário Mais Velho", "Nome: " + maisVelho.getNome() + ", Idade: " + maisVelho.getIdade() + " anos.");
        } else {
            view.clearTable();
            view.displayMessage("Erro", "Nenhum funcionário encontrado.");
        }
    }

    public void imprimirFuncionariosOrdenadosPorNome() {
        List<Funcionario> funcionariosOrdenados = service.getFuncionariosPorOrdemAlfabetica();
        view.exibirFuncionarios(funcionariosOrdenados);
        view.displayMessage("Ordem Alfabética", "Funcionários exibidos em ordem alfabética.");
    }

    public void imprimirTotalSalarios() {
        BigDecimal totalSalarios = service.getTotalSalarios();
        String valorFormatado = view.formatarBigDecimal(totalSalarios);
        view.displayMessage("Total de Salários", "O valor total dos salários é: R$ " + valorFormatado);
    }

    public void imprimirSalariosMinimos(BigDecimal salarioMinimo) {
        Map<String, BigDecimal> salariosMinimos = service.getSalariosMinimos(salarioMinimo);

        StringBuilder sb = new StringBuilder();
        salariosMinimos.forEach((nome, valor)
                -> sb.append(String.format("Nome: %s, ganha %.2f salários mínimos%n", nome, valor))
        );

        view.displayMessage("Quantidade de Salários Mínimos", sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Imprimir Todos":
                imprimirTodosFuncionarios();
                view.displayMessage("Listagem", "Exibindo todos os funcionários atuais.");
                break;
            case "Remover João":
                removerFuncionario("João");
                imprimirTodosFuncionarios();
                break;
            case "Aumento de 10%":
                aplicarAumento(new BigDecimal("0.10"));
                imprimirTodosFuncionarios();
                break;
            case "Agrupar por Função":
                imprimirFuncionariosAgrupadosPorFuncao();
                break;
            case "Aniversariantes (Out/Dez)":
                imprimirAniversariantes(10, 12);
                break;
            case "Funcionário Mais Velho":
                imprimirFuncionarioMaisVelho();
                break;
            case "Ordem Alfabética":
                imprimirFuncionariosOrdenadosPorNome();
                break;
            case "Total de Salários":
                imprimirTotalSalarios();
                break;
            case "Salários Mínimos":
                imprimirSalariosMinimos(new BigDecimal("1212.00"));
                break;
        }
    }
}

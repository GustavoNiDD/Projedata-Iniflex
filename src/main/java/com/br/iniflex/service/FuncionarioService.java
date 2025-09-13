package com.br.iniflex.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.iniflex.models.Funcionario;

@Service
public class FuncionarioService {

    private List<Funcionario> funcionarios;

    public FuncionarioService() {
        this.funcionarios = new ArrayList<>();
    }

    public void inserirFuncionarios(List<Funcionario> novosFuncionarios) {
        this.funcionarios.addAll(novosFuncionarios);
    }

    public void removerFuncionario(String nome) {
        this.funcionarios.removeIf(f -> f.getNome().equals(nome));
    }

    public List<Funcionario> getTodosFuncionarios() {
        return new ArrayList<>(this.funcionarios);
    }

    public void darAumento(BigDecimal percentual) {
        this.funcionarios.forEach(f -> {
            BigDecimal novoSalario = f.getSalario().multiply(BigDecimal.ONE.add(percentual));
            f.setSalario(novoSalario);
        });
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return this.funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public List<Funcionario> getAniversariantesPorMes(int... meses) {
        return this.funcionarios.stream()
                .filter(f -> {
                    for (int mes : meses) {
                        if (f.getDataNascimento().getMonthValue() == mes) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public Funcionario getFuncionarioMaiorIdade() {
        return this.funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
    }

    public List<Funcionario> getFuncionariosPorOrdemAlfabetica() {
        return this.funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalSalarios() {
        return this.funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, BigDecimal> getSalariosMinimos(BigDecimal salarioMinimo) {
        if (salarioMinimo.compareTo(BigDecimal.ZERO) == 0) {
            return java.util.Collections.emptyMap();
        }
        return this.funcionarios.stream()
                .collect(Collectors.toMap(
                        Funcionario::getNome,
                        f -> f.getSalario().divide(salarioMinimo, 2, java.math.RoundingMode.HALF_UP)
                ));
    }
}

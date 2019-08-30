package tech.limelight.limecash.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.model.Budget;
import tech.limelight.limecash.model.BudgetTableRowModel;
import tech.limelight.limecash.repository.BudgetRepository;
import tech.limelight.limecash.service.RepoModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BudgetController {

    private BudgetRepository budgetRepository;
    private RepoModifier repoModifier;

    public BudgetController(BudgetRepository budgetRepository, RepoModifier repoModifier) {
        this.budgetRepository = budgetRepository;
        this.repoModifier = repoModifier;
    }

    @GetMapping("/getAllBudgets")
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll().stream().filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
    }

    @GetMapping("/getAllBudgetRows")
    public List<BudgetTableRowModel> getAllBudgetRows() {
        Stream<Budget> budgetStream = budgetRepository.findAll().stream().filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()));
        Budget budget = budgetStream.collect(Collectors.toList()).get(0);
        String[] areas = budget.getAreas()[0];
        List<BudgetTableRowModel> rows = new ArrayList<>();
        Double[][] budg = budget.getAreaAllowance();
        Double[][] reformedBudg = new Double[budg[0].length][budg.length];
        for(int i = 0; i < budg[0].length; i++) {
            for(int j = 0; j < budg.length; j++) {
                reformedBudg[i][j] = budg[j][i];
            }
        }
        System.out.println(budg);
        System.out.println(reformedBudg);
        for(int i = 0; i < areas.length; i++) rows.add(new BudgetTableRowModel(areas[i], reformedBudg[i]));
        return rows;
    }

    @PostMapping("/setAllBudgetAreas")
    public void setBudgets(@RequestBody List<BudgetTableRowModel> table){
        repoModifier.updateBudgetFromTable(table);
    }


}

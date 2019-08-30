package tech.limelight.limecash.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.limelight.limecash.model.Budget;
import tech.limelight.limecash.model.BudgetTableRowModel;
import tech.limelight.limecash.repository.BudgetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepoModifier {

    private BudgetRepository budgetRepository;

    @Autowired
    public RepoModifier(BudgetRepository budgetRepository){
        this.budgetRepository = budgetRepository;
    }

    public void updateBudgetFromTable(List<BudgetTableRowModel> table){
        Budget budgetStream = budgetRepository.findAll().stream().filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList()).get(0);
        budgetRepository.delete(budgetStream);
        for(BudgetTableRowModel row : table){
            for(int a = 0; a < budgetStream.getAreas()[0].length;a++){
                if (budgetStream.getAreas()[0][a].equals(row.getBudgetName())){
                    for (int i = 0; i < 12; i++) {
                        budgetStream.setValue(a,i,row.getMonths()[i]);
                    }
                }
            }
        }
        budgetRepository.save(budgetStream);
    }

}

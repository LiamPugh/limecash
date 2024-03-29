package tech.limelight.limecash.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.limelight.limecash.model.*;
import tech.limelight.limecash.repository.BudgetRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BudgetController {

    private BudgetRepository budgetRepository;

    public BudgetController(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    void addTransToBudget(Transaction trans) {
        int month = trans.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() + 6;
        if (month > 12) month -= 12;
        LocalDate localDate = trans.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Budget> budgetList = getAllBudgets().stream().filter(a -> !a.getCredit()).collect(Collectors.toList());
        for (Budget budget : budgetList) {
            if (budget.getArea().equals(trans.getAreaImpacted()) && budget.getYear().equals((long) localDate.getYear())) {
                Double[] remaining = budget.getRemaining();
                Double[] monthlySpend = budget.getMonthlyMovement();
                if (trans.getIncoming()) {
                    remaining[month] += trans.getValue();
                } else {
                    remaining[month] -= trans.getValue();
                    monthlySpend[month] += trans.getValue();
                }
                budget.setRemaining(remaining);
                budget.setMonthlyMovement(monthlySpend);
                break;
            }
        }
        budgetRepository.saveAll(budgetList);
    }

    @GetMapping("/getAllBudgets")
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll().stream().filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
    }

    @GetMapping("/getAllIncomes/{yearString}")
    public List<IncomeTableRowModel> getAllIncomes(@PathVariable String yearString) {
        ArrayList<IncomeTableRowModel> incomeTableRows = new ArrayList<>();
        List<Budget> budgetRows = budgetRepository.findAll().stream().filter(a -> a.getCredit() && a.getYear().equals(Long.parseLong(yearString)))
                .filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        for(Budget budget : budgetRows){
            Double[] lows = budget.getMonthlyMovement();
            boolean pass = true;
            for(Double low : lows){
                if(low >= 0){
                    pass = false;
                    break;
                }
            }
            if(pass) {
                incomeTableRows.add(new IncomeTableRowModel(budget.getArea(), budget.getAreaTotal(), budget.getRemaining(), budget.getId()));
            }
        }
        return incomeTableRows;
    }

    @GetMapping("/getAllAreas")
    public List<String> getAllAreas() {
        ArrayList<String> budgetAreas = new ArrayList<>();
        for (Budget budget : getAllBudgets()) {
            budgetAreas.add(budget.getArea());
        }
        return budgetAreas;
    }

    @GetMapping("/submitIncomeCompleted/{incomeName}/{monthNumberString}/{yearString}")
    public void submitIncomeComplete(@PathVariable String incomeName, @PathVariable String monthNumberString, @PathVariable String yearString) {
        List<Budget> budgets = getAllBudgets();
        int monthNum = Integer.parseInt(monthNumberString);
        for(Budget budget : budgets){
            if(budget.getYear().equals(Long.parseLong(yearString)) && budget.getArea().equals(incomeName) && budget.getCredit()){
                Double[] doubles = budget.getRemaining();
                doubles[monthNum] = budget.getAreaTotal()[monthNum];
                budget.setRemaining(doubles);
                budgetRepository.save(budget);
                break;
            }
        }
    }

    @GetMapping("/submitIncomeNotCompleted/{incomeName}/{monthNumberString}/{yearString}")
    public void submitIncomeNotComplete(@PathVariable String incomeName, @PathVariable String monthNumberString, @PathVariable String yearString) {
        List<Budget> budgets = getAllBudgets();
        int monthNum = Integer.parseInt(monthNumberString);
        for(Budget budget : budgets){
            if(budget.getYear().equals(Long.parseLong(yearString)) && budget.getArea().equals(incomeName)){
                Double[] doubles = budget.getRemaining();
                doubles[monthNum] = 0.0;
                budget.setRemaining(doubles);
                budgetRepository.save(budget);
                break;
            }
        }
    }


    private List<DebitsTableRowModel> getDebitsRowModel(List<Budget> budgets, Long year) {
        ArrayList<DebitsTableRowModel> rows = new ArrayList<>();
        for (Budget budget : budgets) {
            if (!budget.getCredit() && budget.getYear().equals(year)) {
                rows.add(new DebitsTableRowModel(budget.getArea(), budget.getAreaTotal(),budget.getId()));
            }
        }
        return rows;
    }

    @GetMapping("/getAllBudgetRows/{yearString}")
    public List<DebitsTableRowModel> getAllBudgetRows(@PathVariable String yearString) {
        Long year = Long.parseLong(yearString);
        return getDebitsRowModel(getAllBudgets(), year);
    }

    @PostMapping("/setAllBudgetAreas/{yearString}")
    public void setBudgets(@RequestBody List<DebitsTableRowModel> table, @PathVariable String yearString) {
        Long year = Long.parseLong(yearString);
        List<Budget> budgetList = getAllBudgets();
        for (DebitsTableRowModel debitsTableRowModel : table) {
            for (Budget budget : budgetList) {
                if (budget.getId().equals(debitsTableRowModel.getId())) {
                    budget.setArea(debitsTableRowModel.getBudgetName());
                    budget.setYear(year);
                    for (int month = 0; month < 12; month++) {
                        Double difference = budget.getAreaTotal()[month] - debitsTableRowModel.getMonths()[month];
                        Double[] areaTotal = budget.getAreaTotal();
                        Double[] remaining = budget.getRemaining();
                        areaTotal[month] = areaTotal[month] - difference;
                        remaining[month] = areaTotal[month] - budget.getMonthlyMovement()[month];
                        budget.setAreaTotal(areaTotal);
                        budget.setRemaining(remaining);
                    }
                }
            }
        }
        budgetRepository.saveAll(budgetList);
    }


    @GetMapping("/getMonthsBudgets/{month}/{yearString}")
    public MonthBudget getMonthsBudgets(@PathVariable String month, @PathVariable String yearString) {
        Long year = Long.parseLong(yearString);
        String[] months = {"June", "July", "August", "September", "October", "November", "December", "January", "February", "March", "April", "May"};
        int monthNum = 0;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(month)) {
                monthNum = i;
                break;
            }
        }

        MonthBudget monthBudget = new MonthBudget();
        ArrayList<String> areas = new ArrayList<>();
        ArrayList<Double> total = new ArrayList<>();
        ArrayList<Double> remaining = new ArrayList<>();
        ArrayList<Double> spent = new ArrayList<>();

        for (Budget budget : getAllBudgets()) {
            if (!budget.getCredit() && budget.getYear().equals(year)) {
                areas.add(budget.getArea());
                total.add(budget.getAreaTotal()[monthNum]);
                remaining.add(budget.getRemaining()[monthNum]);
                spent.add(budget.getMonthlyMovement()[monthNum]);
            }
        }

        monthBudget.setAreas(areas.toArray(new String[0]));
        monthBudget.setRemaining(remaining.toArray(new Double[0]));
        monthBudget.setMonthlySpend(spent.toArray(new Double[0]));
        monthBudget.setAreaAllowance(total.toArray(new Double[0]));
        return monthBudget;
    }

    @GetMapping("/addBudget/{year}")
    public void addBudget(@PathVariable Long year){
        Budget budget = new Budget();
        budget.setYear(year);
        budget.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        budget.setCredit(false);
        Double[] areaTotal = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        budget.setAreaTotal(areaTotal);
        budget.setArea("New Area");
        Budget budget1 = budgetRepository.save(budget);
        budgetRepository.flush();
    }

    @GetMapping("/deleteBudget/{id}")
    public void deleteBudget(@PathVariable Long id){
        Optional<Budget> budget = budgetRepository.findById(id);
        if(budget.isPresent() && budget.get().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())) budgetRepository.deleteById(id);
    }

    @GetMapping("/addIncome/{year}")
    public void addIncome(@PathVariable Long year){
        Budget budget = new Budget();
        budget.setYear(year);
        budget.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        budget.setCredit(true);
        Double[] areaTotal = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
        Double[] remaining = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        Double[] lows = {-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1};
        budget.setAreaTotal(areaTotal);
        budget.setRemaining(remaining);
        budget.setMonthlyMovement(lows);
        budget.setArea("New Area");
        Budget budget1 = budgetRepository.save(budget);
        budgetRepository.flush();
    }

}

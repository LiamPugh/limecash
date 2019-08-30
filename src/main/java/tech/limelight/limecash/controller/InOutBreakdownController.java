package tech.limelight.limecash.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.model.InOutBreakdown;
import tech.limelight.limecash.repository.InOutBreakdownRepository;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InOutBreakdownController {

    private InOutBreakdownRepository inOutBreakdownRepository;
    private DecimalFormat df = new DecimalFormat("#.00");


    public InOutBreakdownController(InOutBreakdownRepository inOutBreakdownRepository) {
        this.inOutBreakdownRepository = inOutBreakdownRepository;
    }

    @GetMapping("/getLowestPoint")
    public String getLowestPoint(){
        List<InOutBreakdown> values = inOutBreakdownRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        Double[] results = values.get(0).getResult();
        Double low = Double.MAX_VALUE;
        for(Double result : results) low = result < low?result:low;
        return df.format(low);
    }


    @GetMapping("/getFinishingTotal")
    public String getFinishingTotal(){
        List<InOutBreakdown> values = inOutBreakdownRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        Double[] results = values.get(0).getResult();
        return df.format(results[4]);
    }

    @GetMapping("/getIncomeTotal")
    public String getIncomeTotal(){
        List<InOutBreakdown> values = inOutBreakdownRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        Double[] results = values.get(0).getInValue();
        double total = 0.0;
        for(Double result : results) total += result;
        return df.format(total);
    }

    @GetMapping("/getCostsTotal")
    public String getCostsTotal(){
        List<InOutBreakdown> values = inOutBreakdownRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        Double[] results = values.get(0).getOutValue();
        double total = 0.00;
        for(Double result : results) total += result.intValue();
        return df.format(total);
    }

    @GetMapping("/getMonthlyCost")
    public String getMonthCosts(){
        List<InOutBreakdown> inOutBreakdown = inOutBreakdownRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        return df.format(inOutBreakdown.get(0).getOutValue()[Integer.parseInt(simpleDateFormat.format(cal.getTime())) - 1]);
    }


}

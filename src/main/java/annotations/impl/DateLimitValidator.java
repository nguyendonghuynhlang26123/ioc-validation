package annotations.impl;

import annotations.DateLimit;
import utils.Helper;
import validator.Validator;
import validator.impl.BaseValidator;
import java.time.chrono.ChronoLocalDate;
import java.util.zip.DataFormatException;

public class DateLimitValidator extends BaseValidator<DateLimit, ChronoLocalDate> {
    ChronoLocalDate start;
    ChronoLocalDate end;
    public DateLimitValidator(){};
    public DateLimitValidator(DateLimitValidator other){
        super(other);
        this.start=other.start;
        this.end=other.end;
    }
    public DateLimitValidator(ChronoLocalDate start,ChronoLocalDate end){
        this.start=start;
        this.end=end;
    }
    @Override
    public boolean isValid(ChronoLocalDate value) {
        boolean result = value == null;
        if(start!=null){
            result = result || start.isBefore(value);
        }
        if(end!=null){
            result = result || end.isAfter(value);
        }
        return result;
    }

    @Override
    public Validator<ChronoLocalDate> cloneValidator() {
        return new DateLimitValidator(this);
    }

    @Override
    public void onInit(DateLimit dateLimit) {
        try {
            start= Helper.parseDate(dateLimit.start());
            end = Helper.parseDate(dateLimit.end());
        } catch (DataFormatException e) {
            e.printStackTrace();
            //TODO: handle unaccepted date format
        }
    }
}

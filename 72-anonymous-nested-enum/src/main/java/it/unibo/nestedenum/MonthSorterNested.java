package it.unibo.nestedenum;

import java.util.Comparator;
//import java.util.Locale;
//import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    //Class Fields
    private Comparator<String> ByDays = new SortByDate();
    private Comparator<String> ByMonth = new SortByMonthOrder();

    //Class Method
    @Override
    public Comparator<String> sortByDays() {
        return ByDays;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return ByMonth;
    }

    //Class Nest
    private enum Month{
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);
        
        //Class Fields
        private int days;

        //Class Constructor
        private Month(int days) {
            this.days = days;
        }
        
        //Class Method
        static Month fromString(String monthName) {
            
            Objects.requireNonNull(monthName);

            try {
                return Month.valueOf(monthName);
            } catch (final IllegalArgumentException e) {
                
                Month monthSearched = null;
                int counter = 0;

                for (final Month value  : Month.values()) {

                    if (value.toString().toLowerCase().startsWith(monthName.toLowerCase())) {

                        monthSearched = value;
                        counter++;

                    }
                
                }
                if ( counter == 0 || counter > 1 ) {
                    throw new IllegalArgumentException("No Month with such a name or too many with the specified letters"
                    + "\n" 
                    + counter 
                    +" letter passed match with possibile Month name" );
                }
                
                return monthSearched;
            }
                
        }
    }
    
    //Nested Class
    private static class SortByMonthOrder implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            final Month first = Month.fromString(o1);
            final Month second = Month.fromString(o2);
            Objects.requireNonNull(first);
            return first.compareTo(second);
        }
    }
    
    private static class SortByDate implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            final Month first = Month.fromString(o1);
            final Month second = Month.fromString(o2);
            Objects.requireNonNull(first);
            return Integer.compare(first.days, second.days);
        }
    }

}

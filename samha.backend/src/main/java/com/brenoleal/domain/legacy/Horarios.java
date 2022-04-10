package com.brenoleal.domain.legacy;

public abstract class Horarios {
    
    public static final String MATUTINO_1 = "07:00 - 07:50";
    public static final String MATUTINO_2 = "07:55 - 08:45";
    public static final String MATUTINO_3 = "08:50 - 09:40";
    public static final String MATUTINO_4 = "10:00 - 10:50";
    public static final String MATUTINO_5 = "10:55 - 11:45";
    public static final String MATUTINO_6 = "11:50 - 12:40";
    
    public static final String VESPERTINO_1 = "12:50 - 13:40";
    public static final String VESPERTINO_2 = "13:45 - 14:35";
    public static final String VESPERTINO_3 = "14:40 - 15:30";
    public static final String VESPERTINO_4 = "15:50 - 16:40";
    public static final String VESPERTINO_5 = "16:45 - 17:35";
    public static final String VESPERTINO_6 = "17:40 - 18:30";
    
    public static final String NOTURNO_1 = "18:50 - 19:35";
    public static final String NOTURNO_2 = "19:35 - 20:20";
    public static final String NOTURNO_3 = "20:30 - 21:15";
    public static final String NOTURNO_4 = "21:15 - 22:00";
    public static final String NOTURNO_5 = "--";
    public static final String NOTURNO_6 = "--";
    
    public static final String INICIAL_0 = "07:00";
    public static final String INICIAL_1 = "07:55";
    public static final String INICIAL_2 = "08:50";
    public static final String INICIAL_3 = "10:00";
    public static final String INICIAL_4 = "10:55";
    public static final String INICIAL_5 = "11:50";
    public static final String INICIAL_6 = "12:50";
    public static final String INICIAL_7 = "13:45";
    public static final String INICIAL_8 = "14:40";
    public static final String INICIAL_9 = "15:50";
    public static final String INICIAL_10 = "16:45";
    public static final String INICIAL_11 = "17:40";
    public static final String INICIAL_12 = "18:50";
    public static final String INICIAL_13 = "19:35";
    public static final String INICIAL_14 = "20:30";
    public static final String INICIAL_15 = "21:15";
    public static final String INICIAL_16 = "22:00";
    public static final String INICIAL_17 = "22:45";
    
    public static final String FINAL_0 = "07:50";
    public static final String FINAL_1 = "08:45";
    public static final String FINAL_2 = "09:40";
    public static final String FINAL_3 = "10:50";
    public static final String FINAL_4 = "11:45";
    public static final String FINAL_5 = "12:40";
    public static final String FINAL_6 = "13:40";
    public static final String FINAL_7 = "14:35";
    public static final String FINAL_8 = "15:30";
    public static final String FINAL_9 = "16:40";
    public static final String FINAL_10 = "17:35";
    public static final String FINAL_11 = "18:30";
    public static final String FINAL_12 = "19:35";
    public static final String FINAL_13 = "20:20";
    public static final String FINAL_14 = "21:15";
    public static final String FINAL_15 = "22:00";
    public static final String FINAL_16 = "22:45";
    public static final String FINAL_17 = "23:30";
    
    public static String horarioInicial(int numero){
        
        switch(numero){
            
            case 0: return INICIAL_0;
            case 1: return INICIAL_1;
            case 2: return INICIAL_2;
            case 3: return INICIAL_3;
            case 4: return INICIAL_4;
            case 5: return INICIAL_5;
            case 6: return INICIAL_6;
            case 7: return INICIAL_7;
            case 8: return INICIAL_8;
            case 9: return INICIAL_9;
            case 10: return INICIAL_10;
            case 11: return INICIAL_11;
            case 12: return INICIAL_12;
            case 13: return INICIAL_13;
            case 14: return INICIAL_14;
            case 15: return INICIAL_15;
            case 16: return INICIAL_16;
            default: return INICIAL_17;
            
        }
    }
    
    public static String horarioFinal(int numero){
        
        switch(numero){
            
            case 0: return FINAL_0;
            case 1: return FINAL_1;
            case 2: return FINAL_2;
            case 3: return FINAL_3;
            case 4: return FINAL_4;
            case 5: return FINAL_5;
            case 6: return FINAL_6;
            case 7: return FINAL_7;
            case 8: return FINAL_8;
            case 9: return FINAL_9;
            case 10: return FINAL_10;
            case 11: return FINAL_11;
            case 12: return FINAL_12;
            case 13: return FINAL_13;
            case 14: return FINAL_14;
            case 15: return FINAL_15;
            case 16: return FINAL_16;
            default: return FINAL_17;
            
        }
    }
}

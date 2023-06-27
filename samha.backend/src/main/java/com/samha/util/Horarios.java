package com.samha.util;

import com.samha.domain.Aula;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public abstract class Horarios {

    public static final int TEMPO_MAXIMO = 1;
    public static final int INTERVALO_MINIMO = 2;

    public static final int INTERVALO_MINIMO_ALMOCO = 1;

    public static final String SEGUNDA = "SEGUNDA-FEIRA";
    public static final String TERCA = "TERÇA-FEIRA";
    public static final String QUARTA = "QUARTA-FEIRA";
    public static final String QUINTA = "QUINTA-FEIRA";
    public static final String SEXTA = "SEXTA-FEIRA";
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

    public static String obterStringDia(int dia){

        switch(dia){

            case 0:
                return SEGUNDA;
            case 1:
                return TERCA;
            case 2:
                return QUARTA;
            case 3:
                return QUINTA;
            default:
                return SEXTA;
        }
    }

    public static double obterQuantidadeHoras(Aula primeira, Aula ultima, int flag) {

        String horarioInicial = Horarios.horarioInicial(primeira.getNumero());
        String horarioFinal = Horarios.horarioFinal(ultima.getNumero());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

        LocalTime inicio = LocalTime.parse(horarioInicial, formato);
        LocalTime fim = LocalTime.parse(horarioFinal, formato);

        double qtHoras = calcularDiferencaHoras(inicio, fim, formato);

        if (flag == Horarios.INTERVALO_MINIMO)
            return modificarQuantidadeHorasIntervaloMinimo(fim, inicio, qtHoras);
        else
            return qtHoras;
    }

    public static double calcularDiferencaHoras(LocalTime inicio, LocalTime fim, DateTimeFormatter formato) {

        LocalTime diferenca = fim.minusHours(inicio.getHour()).minusMinutes(inicio.getMinute());
        String dif = diferenca.format(formato);
        String[] parts = dif.split(":");
        Integer hour = Integer.parseInt(parts[0]);
        Integer minutes = Integer.parseInt(parts[1]);
        //porcentagem de uma hora

        return hour + (minutes / 60D);
    }

    public static double modificarQuantidadeHorasIntervaloMinimo(LocalTime fim, LocalTime inicio, double qtHoras) {

        if (fim.getHour() == inicio.getHour()) {

            if (fim.getMinute() < inicio.getMinute()) {
                return qtHoras;
            }

        } else if (fim.getHour() < inicio.getHour()) {
            return qtHoras;
        }

        return 24 - qtHoras;
    }

    public static LocalTime getTimeFromDouble(double tempo) {
        Double tempoClass = tempo;
        Long hour = tempoClass.longValue();
        Long minutes =  Math.round((tempo - hour) * 60);
        return LocalTime.of(hour.intValue(), minutes.intValue());

    }
}

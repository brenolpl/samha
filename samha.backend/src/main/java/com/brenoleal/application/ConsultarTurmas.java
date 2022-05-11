package com.brenoleal.application;


import com.brenoleal.application.commons.QueryEntities;
import com.brenoleal.domain.Turma;
import com.brenoleal.persistence.filter.PagedList;
import com.brenoleal.persistence.filter.Query;


import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

public class ConsultarTurmas extends QueryEntities<Turma> {


    public ConsultarTurmas(Query query) {
        super(query, Turma.class);
    }

    @Override
    protected PagedList execute() throws Exception {
        this.tratarEntityQuery();


        return super.execute();
    }

    private void tratarEntityQuery() {
        LinkedHashMap<String, Object> and = (LinkedHashMap<String, Object>) this.entityQuery.getPredicates().get("and");

        if(and != null && !and.isEmpty()) {
            Boolean ativas = (Boolean) and.get("ativas");
            if (ativas) {
                String anoFiltro = (String) and.get("ano");
                String semestreFiltro = (String) and.get("semestre");

                try {
                    Integer anoFiltroInt = Integer.parseInt(anoFiltro);
                    Integer semestreFiltroInt = Integer.parseInt(semestreFiltro);

                    List<Turma> turmas = this.genericRepository.find(Turma.class, q -> q.where(

                    ));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }


            and.remove("ativas");
            and.remove("ano");
            and.remove("semestre");
        }
    }

    public boolean verificarTurmaAtiva(Turma turma, int semestreAtual){

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

        int anoInicial = turma.getAno();
        int semestreInicial = turma.getSemestre();
        String p = null;

        if(turma.getMatriz().getCurso().getNivel().equalsIgnoreCase("ENSINO MÉDIO INTEGRADO")){

            p = calcularAnoAtual(anoAtual, anoInicial);
        }else{

            p = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
        }

        int anoPeriodo = Integer.valueOf(p);

        if(anoPeriodo > turma.getMatriz().getCurso().getQtPeriodos() || anoPeriodo < 1)
            return false;

        return true;
    }

    public String calcularAnoAtual(int anoAtual, int anoInicial){

        int qtAnos = (anoAtual - anoInicial) + 1;
        String ano = String.valueOf(qtAnos);

        return ano;
    }

    public String calcularPeriodoAtual(int anoAtual, int semestreAtual, int anoInicial, int semestreInicial){

        int qtAnos = ((anoAtual - anoInicial) * 2);

        if(semestreAtual == semestreInicial){
            qtAnos+=1;
        }else if(semestreAtual == 2 && semestreInicial == 1){
            qtAnos+=2;
        }

        String periodo = String.valueOf(qtAnos);

        return periodo;
    }
}

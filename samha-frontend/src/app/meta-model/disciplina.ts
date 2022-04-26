import {TableColumnModel} from './table-column-model';

export const disciplinaColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'Id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'sigla', header: 'Sigla', visible: true},
  {columnDef: 'tipo', header: 'Tipo', visible: true},
  {columnDef: 'cargaHoraria', header: 'Carga Horária', visible: true},
  {columnDef: 'qtAulas', header: 'Quantidade de Aulas', visible: true},
  {columnDef: 'periodo', header: 'Período', visible: true}
]

/*
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sigla;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int cargaHoraria;

    @Column(nullable = false)
    private int qtAulas;

    @Column(nullable = false)
    private int periodo;
 */

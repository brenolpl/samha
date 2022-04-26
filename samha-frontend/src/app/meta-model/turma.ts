import {TableColumnModel} from './table-column-model';

export const turmaColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'turno', header: 'Turno', visible: true},
  {columnDef: 'ano', header: 'Ano', visible: true},
  {columnDef: 'semestre', header: 'Semestre', visible: true},
  {columnDef: 'matriz.nome', header: 'Matriz', visible: true},
  {columnDef: 'matriz.curso.nome', header: 'Curso', visible: true}
];

/*
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String turno;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int semestre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matriz_curricular_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    private MatrizCurricular matriz;
 */

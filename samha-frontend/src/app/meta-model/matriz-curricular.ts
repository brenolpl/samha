import {TableColumnModel} from './table-column-model';

export const matrizColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'ano', header: 'Ano', visible: true},
  {columnDef: 'semestre', header: 'Semestre', visible: true},
  {columnDef: 'curso.nome', header: 'Curso', visible: true},
  {columnDef: 'curso', header: 'curso.id', visible: false}
]

/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int semestre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Curso curso;
 */

import {TableColumnModel} from './table-column-model';
import {commonLogColumns} from './log';

export const cursoColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'qtPeriodos', header: 'Períodos', visible: true},
  {columnDef: 'nivel', header: 'Nível', visible: true},
  {columnDef: 'coordenadoria.nome', header: 'Coordenadoria', visible: true},
  {columnDef: 'professor.nome', header: 'Coordenador', visible: true}
]

export const cursoComLogColumns: TableColumnModel[] = [
  {columnDef: 'pk.id', header: 'ID', visible: true},
  {columnDef: 'pk.rev', header: 'Revisão', visible: true},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'qtPeriodos', header: 'Períodos', visible: true},
  {columnDef: 'nivel', header: 'Nível', visible: true},
  {columnDef: 'coordenadoria.nome', header: 'Coordenadoria', visible: true},
  {columnDef: 'professor.nome', header: 'Coordenador', visible: true},
  ...commonLogColumns
]

/*
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private int qtPeriodos;

    @Column(nullable = false)
    private String nivel;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordenadoria_id", nullable = true)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Coordenadoria coordenadoria;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Professor professor;
 */

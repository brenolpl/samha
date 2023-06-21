import {TableColumnModel} from './table-column-model';
import {commonLogColumns} from './log';
import {FieldEnum} from "../shared/field-enum";

export const cursoColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'qtPeriodos', header: 'Períodos', visible: true},
  {columnDef: 'nivel', header: 'Nível', visible: true},
  {columnDef: 'semestral', header: 'Semestral', visible: true, type: FieldEnum.BOOLEAN},
  {columnDef: 'coordenadoria.nome', header: 'Coordenadoria', visible: true},
  {columnDef: 'professor.nome', header: 'Coordenador', visible: true}
]

export const cursoLogColumns: TableColumnModel[] = [
  ...cursoColumns.filter(column => column.columnDef !== 'id'),
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

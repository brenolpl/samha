import {TableColumnModel} from './table-column-model';

export const servidorColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'matricula', header: 'Matrícula', visible: true},
  {columnDef: 'email', header: 'E-mail', visible: true}
]

/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String matricula;

    @Column(unique = true)
    private String email;
 */

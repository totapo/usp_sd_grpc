package servidor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import protobufgencode.Aluno;
import protobufgencode.Materia;
import protobufgencode.Professor;
import protobufgencode.Resultado;
import protobufgencode.Turma;

public class Procedures {
	
	private static Resultado r;
	private static long longResponse = Long.MIN_VALUE;
	
	
	static {
		List<Aluno> lista, apr,repr,rec;
		lista = new ArrayList<Aluno>();
		apr = new ArrayList<Aluno>();
		repr = new ArrayList<Aluno>();
		rec = new ArrayList<Aluno>();
		
		double media,mediaturma;
		
		mediaturma=0;
		
		List<Double> n;
		Aluno alunoAux;
		
		Random ra = new Random();
		for(int i=0; i<60; i++) {
			n = new ArrayList<Double>();
			media=0;
			
			for(int a=0; a<3; a++) {
				n.add(ra.nextDouble()*10.0);
			}
			
			for(Double d : n) {
				media+=d;
			}
			
			media = media/n.size();
			
			alunoAux=Aluno.newBuilder().setId(i).setMedia(media).setNome("Aluno"+i).addAllNotas(n).build();
			
			if(media<3.0) {
				repr.add(alunoAux);
			} else if(media<5.0) {
				rec.add(alunoAux);
			} else {
				apr.add(alunoAux);
			}
			
			mediaturma+=media;
			
			lista.add(alunoAux);
		}
		
		mediaturma = mediaturma/lista.size();
		
		Professor p = Professor.newBuilder().setNome("Professor").setId(Long.MAX_VALUE).build();
		
		Materia m = Materia.newBuilder().setCodigo("SERVIDOR").setNome("Materia turma servidor").setDescricao("Descricao").build();
		
		Turma t = Turma.newBuilder()
				.setMateria(m)
				.setProfessor(p)
				.addAllAlunos(lista)
				.build();
		
		r = Resultado.newBuilder()
				.setMediaTurma(mediaturma)
				.addAllRecuperacao(rec)
				.addAllAprovados(apr)
				.addAllReprovados(repr)
				.setTurma(t).build();
	}
	
	public static long longRequest(long valor){
		return longResponse;
	}
	
	public static long eightLongRequest(long val1, long val2, long val3, long val4, long val5, long val6, long val7, long val8){
		return longResponse;
	}
	
	public static long eightLongArrayRequest(List<Long> l){
		/*long resp=0;
		for(int i=0; i<longs.length; i++){
			resp-=longs[i];
		}*/
		return longResponse;
	}
	
	public static String testStringLenghtRequest(String valor){
		return valor;
	}
	
	public static void testException(){
		int a = 1/0;
		if(a==0) return; //explodir
	}
	
	public static Collection<Aluno> testCollections(Collection<Aluno> alunos){
		/*LinkedList<Aluno> entrada = new LinkedList<Aluno>(alunos);
		LinkedList<Aluno> saida = new LinkedList<Aluno>();
		Iterator<Aluno> it = entrada.descendingIterator();
		while(it.hasNext()){
			saida.add(it.next());
		}
		return saida;*/
		return r.getTurma().getAlunosList();
	}
	
	public static Resultado testComplex(Turma t){
		/*ArrayList<Aluno> aprovados, reprovados, recuperacao, turmaAlunos;
		
		
		aprovados = new ArrayList<Aluno>();
		reprovados = new ArrayList<Aluno>();
		recuperacao = new ArrayList<Aluno>();
		turmaAlunos = new ArrayList<Aluno>();
		
		double mediaSala=0;
		double mediaAluno;
		Aluno aux;
		Professor auxP;
		Turma auxT;
		
		for(Aluno a : t.getAlunosList()){
			mediaAluno=0.0;
			for(double d : a.getNotasList()){
				mediaAluno+=d;
			}
			mediaAluno = mediaAluno/a.getNotasCount();
			aux = Aluno.newBuilder()
					.addAllNotas(a.getNotasList())
					.setNome(a.getNome())
					.setMedia(mediaAluno)
					.setId(a.getId())
					.build();
			if(mediaAluno<3.0) reprovados.add(aux);
			else if (mediaAluno>=5.0) aprovados.add(aux);
			else recuperacao.add(aux);
			
			turmaAlunos.add(aux);
			mediaSala+=mediaAluno;
		}
		
		mediaSala=mediaSala/t.getAlunosList().size();
		
		auxP = Professor.newBuilder()
				.setId(t.getProfessor().getId())
				.setNome(t.getProfessor().getNome())
				.build();
		
		auxT = Turma.newBuilder()
				.addAllAlunos(turmaAlunos)
				.setProfessor(auxP)
				.setMateria(t.getMateria())
				.build();
		
		Resultado r = Resultado.newBuilder()
				.addAllAprovados(aprovados)
				.addAllReprovados(reprovados)
				.addAllRecuperacao(recuperacao)
				.setTurma(auxT)
				.setMediaTurma(mediaSala)
				.build();
		*/
		return r;
	}

	public static void testManyArguments(){
		
	}
}

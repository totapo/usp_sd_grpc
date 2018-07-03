package servidor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import protobufgencode.Aluno;
import protobufgencode.Professor;
import protobufgencode.Resultado;
import protobufgencode.Turma;

public class Procedures {
	
	public static long longRequest(long valor){
		return -valor;
	}
	
	public static long eightLongRequest(long val1, long val2, long val3, long val4, long val5, long val6, long val7, long val8){
		return val1+val2+val3+val4+val5+val6+val7+val8;
	}
	
	public static long eightLongArrayRequest(Long[] longs){
		long resp=0;
		for(int i=0; i<longs.length; i++){
			resp-=longs[i];
		}
		return resp;
	}
	
	public static String testStringLenghtRequest(String valor){
		return valor.toUpperCase();
	}
	
	public static void testException(){
		int a = 1/0;
		if(a==0) return; //explodir
	}
	
	public static Collection<Aluno> testCollections(Collection<Aluno> alunos){
		LinkedList<Aluno> entrada = new LinkedList<Aluno>(alunos);
		LinkedList<Aluno> saida = new LinkedList<Aluno>();
		Iterator<Aluno> it = entrada.descendingIterator();
		while(it.hasNext()){
			saida.add(it.next());
		}
		return saida;
	}
	
	public static Resultado testComplex(Turma t){
		ArrayList<Aluno> aprovados, reprovados, recuperacao, turmaAlunos;
		
		
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
		
		return r;
	}

	public static void testManyArguments(){
		
	}
}

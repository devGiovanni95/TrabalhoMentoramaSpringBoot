package br.com.cadastro.alunos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunosController  {

    private final List<Alunos> alunos;

    public AlunosController(List<Alunos> alunos) {

        this.alunos = new ArrayList<>();
    }


    @GetMapping
    public  List<Alunos> findAll(@RequestParam(required = false) String nome, Integer idade){
        if (nome != null){
            return alunos.stream().filter(msg -> msg.getNome().contains(nome)).collect(Collectors.toList());
        }
        if (idade != null){
            return alunos.stream().filter(msg -> msg.getIdade().equals(idade)).collect(Collectors.toList());
        }
        return this.alunos;
    }

    @GetMapping("/{id}")
    public Alunos findById(@PathVariable("id") Integer id) {
        return this.alunos.stream().filter(alunoId -> alunoId.getId().equals(id)).findFirst().orElse(null);
    }


    @PostMapping
    public ResponseEntity add(@RequestBody Alunos aluno){
        if (aluno.getId() == null){
            aluno.setId(alunos.size() + 1 );
        }else if (alunos.contains(aluno)){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }else
            alunos.add(aluno);
            return new ResponseEntity(HttpStatus.CREATED);

    }

//    @PostMapping
//    public ResponseEntity<Alunos> add(@RequestBody Alunos registroEscolar, @RequestBody Alunos aluno,@RequestBody Alunos old) {
//        if (registroEscolar.getId() == null && aluno.getIdade() == null && old.getIdade() == null) {
////            aluno.setId(alunos.size() + 1);
////            aluno.setIdade();
////            aluno.setNome();
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }else if (registroEscolar.getId() != null && aluno.getNome() != null && old.getIdade() != null){
//            registroEscolar.setId(registroEscolar.getId());
//            aluno.setNome(aluno.getNome());
//            old.setIdade(old.getIdade());
//        }
//        alunos.add(registroEscolar);
//        alunos.add(aluno);
//        alunos.add(old);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }

    @PutMapping
    public ResponseEntity update(@RequestBody final Alunos aluno) {
        alunos.stream().filter(nomeAluno -> nomeAluno.getId().equals(aluno.getId()))
                .forEach(nomeAluno -> nomeAluno.setNome(aluno.getNome()));

        alunos.stream()
                .filter(nomeAluno -> nomeAluno.getId().equals(aluno.getId()))
                .forEach(nomeAluno -> nomeAluno.setIdade(aluno.getIdade()));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        alunos.removeIf(aluno -> aluno.getId().equals(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

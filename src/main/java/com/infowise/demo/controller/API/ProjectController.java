package com.infowise.demo.controller.API;


import com.infowise.demo.Service.ProjectService;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.ProjectDTO;
import com.infowise.demo.rep.ProjectRep;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("project")
    public Header<ProjectDTO> create(@RequestBody Header<ProjectDTO> request){
        System.out.println(request+ "프로젝트 등록");
        return projectService.create(request.getData());
    }

    @GetMapping("project/{idx}")
    public ProjectRep read(@PathVariable(name="idx") Long idx) {return ProjectRep.fromDTO(projectService.read(idx));}


    @PutMapping("project/{idx}")
    public Header<ProjectDTO> update(@PathVariable(name="idx") Long idx,
                                     @RequestBody Header<ProjectDTO> request){
        return projectService.update(idx, request.getData());
    }

    @DeleteMapping("project/{idx}")
    public Header delete(@PathVariable(name="idx")Long idx){return projectService.delete(idx);}

}

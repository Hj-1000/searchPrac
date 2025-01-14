//12. 매핑 및 결과 메세지 처리

package com.example.search.Controller;

import com.example.search.DTO.StoreDTO;
import com.example.search.Service.StoreService;
import com.example.search.Util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StoreController {
    private final StoreService storeService;
    private final PaginationUtil paginationUtil;

    // 서비스에 메소드를 보면서 작성
    /*******************************************
     함수명 : storeDeleteProc(Integer idx)
     입  력 : html로 부터 삭제할 일련번호
     출  력 : 성공/실패 메세지
     내  용 : 해당번호로 데이터를 삭제 후 메세지를 가지고 list페이지로 이동
     다른맵핑으로 값과 함께 이동할 때는 RedirectAttributes
     *******************************************/
    @GetMapping("/storeDelete")
    public String storeDeleteProc(Integer idx, RedirectAttributes redirectAttributes){
        log.info("삭제할 글 번호 + " + idx);
        Boolean result =
        storeService.storeDelete(idx);
        // 결과의 처리
        if (result){
            // 삭제하셨습니다.
            redirectAttributes.addAttribute("msg", "삭제하였습니다.");
        }else {
            // 삭제를 실패하였습니다.
            redirectAttributes.addAttribute("msg", "삭제를 실패하였습니다.");
        }
        return "redirect:/storeList";
    }
    /*******************************************
     함수명 : storeInsertForm()
     입  력 : 없음
     출  력 : 삽입폼으로 이동
     내  용 : 해당 맵핑의 요청이 있으면 해당 HTML로 이동
     *******************************************/
    @GetMapping("/storeInsert")
    public String storeInsertForm(Model model){
        // 검증 라이브러리 추가하면
        // 입력폼에서 object, field를 이용해서 검증처리
        model.addAttribute("data", new StoreDTO());
        return "insert";
    }
    /*******************************************
     함수명 : storeInsertProc(StoreDTO storeDTO)
     입  력 : 입력한 StoreDTO
     출  력 : 저장 결과 메세지를 가지고 list로 이동
     내  용 : 입력받은 데이터를 데이터베이스에 저장하고, 결과를 가지고 List맵핑으로 이동
     *******************************************/
    @PostMapping("/storeInsert")
    public String storeInsertProc(StoreDTO storeDTO, RedirectAttributes redirectAttributes){
        StoreDTO result = storeService.storeInsert(storeDTO);
        if (result != null){ // 값이 있으면, 저장에 성공했으면
            redirectAttributes.addFlashAttribute("msg","저장을 하였습니다.");
        }else { //저장을 실패했으면
            redirectAttributes.addFlashAttribute("msg", "저장을 실패하였습니다.");
        }
        return "redirect:/storeList";
    }
    /*******************************************
     함수명 : storeUpdateForm(Integer idx)
     입  력 : 수정할 일련번호
     출  력 : 수정할 DTO를 전달
     내  용 : 일련번호로 해당 데이터를 조회해서 결과값을 HTML에 전달
     *******************************************/
    @GetMapping("/storeUpdate")
    public String storeUpdateForm(Integer idx, Model model, RedirectAttributes redirectAttributes){
        StoreDTO storeDTO =
        storeService.storeRead(idx);
        if (storeDTO !=null){
            model.addAttribute("data" , storeDTO);

            return "update";
        }
        //수정할 데이터가 존재하지 않으면
        redirectAttributes.addFlashAttribute("msg", "해당 데이터가 존재하지 않습니다.");
        return "redirect:/storeList";   //수정할 데이터가 존재하지 않으면 목록페이지로 이동
    }

    /*******************************************
     함수명 : storeUpdateProc(StoreDTO storeDTO)
     인  수 : 수정한 DTO
     출  력 : 수정처리 후 결과 메세지
     내  용 : 수정할 데이터를 저장해서 결과메세지를 가지고 list 맵핑으로 이동
     *******************************************/
    @PostMapping("/storeUpdate")
    public String storeUpdateProc(StoreDTO storeDTO, RedirectAttributes redirectAttributes){
        StoreDTO result =
        storeService.storeUpdate(storeDTO);

        if (result != null){ // 수정을 성공했을 때, 결과값이 비어있지 않으면
            redirectAttributes.addFlashAttribute("msg","수정하였습니다.");
        }else {
            redirectAttributes.addFlashAttribute("msg","수정을 실패하였습니다.");
        }
        return "redirect:/storeList";
    }
    /*******************************************
     함수명 : storeReadProc(String idx)
     입  력 : 읽어올 일련번호
     출  력 : 조회된 DTO
     내  용 : 해당번호로 데이터베이스에서 조회하여 결과를 전달(HTML 상세페이지)
     *******************************************/
    @GetMapping("/storeRead")
    public String storeReadProc(Integer idx, RedirectAttributes redirectAttributes, Model model){
        StoreDTO result =
        storeService.storeRead(idx);
        if (result != null){// 조회한 결과가 존재하면
            model.addAttribute("data", result);
            return "read";
        }
        // 조회한 결과가 존재하지 않으면
        redirectAttributes.addFlashAttribute("msg", " 해당하는 데이터가 존재하지 않습니다.");
        return "redirect:/storeList";
    }
    /*******************************************
     함수명 : storeListForm(Pageable pageable, String type, String keyword)
     입  력 : 조회할 페이지 정보, 분류대상, 검색 키워드
     출  력 : Page<StoreDTO>
     내  용 : 분류대상에 키워드로 조회한 해당 페이지 데이터를 전달
     *******************************************/
    @GetMapping({"/", "/storeList"})
    public String storeList(
            @PageableDefault(page = 1) Pageable pageable, // 페이지정보, 페이지정보가 없으면 기본값으로 1페이지
            @RequestParam(value = "type", defaultValue = "") String type, // 검색대상, 없으면 기본값은 null
            @RequestParam(value = "keyword", defaultValue = "")String keyword,// 키워드, 없으면 기본값은 null
            Model model){
        Page<StoreDTO> result = storeService.storeList(pageable, type, keyword);
        Map<String, Integer> pageInfo = paginationUtil.pagination(result);
        model.addAttribute("list", result); //데이터 전달
        model.addAllAttributes(pageInfo);   //페이지 정보
        model.addAttribute("type", type);   // 검색분류
        model.addAttribute("keyword", keyword); // 키워드

        return "list";
    }

}

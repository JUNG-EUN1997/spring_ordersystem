package beyondProjectForOrdersystem.ordering.controller;

import beyondProjectForOrdersystem.ordering.dto.OrderListResDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.security.auth.message.AuthException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class SseController {
//    SseEmitter는 연결된 사용자 정보를 의미
//        String은 email을 의미
//    ConcurrentHashMap은 Thread-safe한 map이다.
//          멀티스레드 상황에서 문제가 없는 맵이다. : 동시성 이슈 발생 X

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

//    유저와 서버가 연결을 시작하는 곳
    @GetMapping("/subscribe")
    public SseEmitter subscribe(){
        SseEmitter emitter = new SseEmitter(14400*60*1000L); // 24시간의 유효시간
//        사용자와 관련된 정보가 들어있는 SseEmitter

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        emitters.put(email,emitter);

        emitter.onCompletion(() -> emitters.remove(email)); // 완료되면 map에서 삭제
        emitter.onTimeout(() -> emitters.remove(email)); // timeout 되어도 map에서 삭제

        try {
            emitter.send(SseEmitter.event().name("connect").data("connect success!"));
        }catch (IOException e){
            e.printStackTrace();
        }

        return emitter;
    }

    public void publicsMessage(OrderListResDto dto, String email){
        SseEmitter emitter = emitters.get(email);
        if(emitter != null){
            try {
                emitter.send(SseEmitter.event().name("ordered").data(dto));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

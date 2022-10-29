package com.inssa.backend.bus.controller;

import com.inssa.backend.ApiDocument;
import com.inssa.backend.bus.controller.dto.BusResponse;
import com.inssa.backend.bus.service.BusService;
import com.inssa.backend.common.domain.ErrorMessage;
import com.inssa.backend.common.domain.Message;
import com.inssa.backend.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusController.class)
public class BusControllerTest extends ApiDocument {

    private static final int NUMBER = 1;
    private static final String NUMBER_PARAMETER_NAME = "number";
    private static final boolean IS_LAST = true;
    private static final String BUS_STOP_NAME = "삼성화재연수원";
    private static String AUTHORIZATION = "Authorization";
    private static String BEARER = "Bearer";
    private static String ACCESS_TOKEN = "{access token generated by JWT}";
    private static Long ID = 1L;

    @MockBean
    private BusService busService;

    private BusResponse busResponse;

    @BeforeEach
    void setUp() {
        List<String> busStops = IntStream.range(0, 2)
                .mapToObj(n -> BUS_STOP_NAME)
                .collect(Collectors.toList());
        busResponse = BusResponse.builder()
                .isLast(IS_LAST)
                .lastVisitedBusStop(BUS_STOP_NAME)
                .busStops(busStops)
                .build();
    }

    @DisplayName("버스 조회 성공")
    @Test
    void get_bus_success() throws Exception {
        // given
        willReturn(busResponse).given(busService).getBus(anyInt());
        // when
        ResultActions resultActions = 버스_조회_요청(NUMBER);
        // then
        버스_조회_성공(resultActions);
    }

    @DisplayName("버스 조회 실패")
    @Test
    void get_bus_fail() throws Exception {
        // given
        willThrow(new NotFoundException(ErrorMessage.NOT_FOUND_BUS)).given(busService).getBus(anyInt());
        // when
        ResultActions resultActions = 버스_조회_요청(NUMBER);
        // then
        버스_조회_실패(resultActions, new Message(ErrorMessage.NOT_FOUND_BUS));
    }

    @DisplayName("버스 즐겨찾기 등록 성공")
    @Test
    void create_bus_like_success() throws Exception {
        // given
        willDoNothing().given(busService).createBusLike(anyLong(), anyInt());
        // when
        ResultActions resultActions = 버스_즐겨찾기_등록_요청(NUMBER);
        // then
        버스_즐겨찾기_등록_성공(resultActions);
    }

    @DisplayName("버스 즐겨찾기 등록 실패")
    @Test
    void create_bus_like_fail() throws Exception {
        // given
        willThrow(new NotFoundException(ErrorMessage.NOT_FOUND_BUS)).given(busService).createBusLike(anyLong(), anyInt());
        // when
        ResultActions resultActions = 버스_즐겨찾기_등록_요청(NUMBER);
        // then
        버스_즐겨찾기_등록_실패(resultActions, new Message(ErrorMessage.NOT_FOUND_BUS));
    }

    @DisplayName("버스 즐겨찾기 삭제 성공")
    @Test
    void delete_bus_like_success() throws Exception {
        // given
        willDoNothing().given(busService).deleteBusLike(anyLong(), anyInt());
        // when
        ResultActions resultActions = 버스_즐겨찾기_삭제_요청(NUMBER);
        // then
        버스_즐겨찾기_삭제_성공(resultActions);
    }

    @DisplayName("버스 즐겨찾기 삭제 실패")
    @Test
    void delete_bus_like_fail() throws Exception {
        // given
        willThrow(new NotFoundException(ErrorMessage.NOT_FOUND_BUS)).given(busService).deleteBusLike(anyLong(), anyInt());
        // when
        ResultActions resultActions = 버스_즐겨찾기_삭제_요청(NUMBER);
        // then
        버스_즐겨찾기_삭제_실패(resultActions, new Message(ErrorMessage.NOT_FOUND_BUS));
    }

    @DisplayName("버스 위치 최신화 성공")
    @Test
    void arrive_at_bus_stop_success() throws Exception {
        // given
        willDoNothing().given(busService).arriveAtBusStop(anyLong());
        // when
        ResultActions resultActions = 버스_위치_최신화_요청(ID);
        // then
        버스_위치_최신화_성공(resultActions);
    }

    private ResultActions 버스_조회_요청(int number) throws Exception {
        return mockMvc.perform(get("/api/v1/buses")
                .contextPath("/api/v1")
                .param(NUMBER_PARAMETER_NAME, String.valueOf(number)));
    }

    private void 버스_조회_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(busResponse)))
                .andDo(print())
                .andDo(toDocument("get-bus-success"));
    }

    private void 버스_조회_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("get-bus-fail"));
    }

    private ResultActions 버스_즐겨찾기_등록_요청(int number) throws Exception {
        return mockMvc.perform(get("/api/v1/buses/like")
                .contextPath("/api/v1")
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .param(NUMBER_PARAMETER_NAME, String.valueOf(number)));
    }

    private void 버스_즐겨찾기_등록_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("create-bus-like-success"));
    }

    private void 버스_즐겨찾기_등록_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("create-bus-like-fail"));
    }

    private ResultActions 버스_즐겨찾기_삭제_요청(int number) throws Exception {
        return mockMvc.perform(delete("/api/v1/buses/like")
                .contextPath("/api/v1")
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .param(NUMBER_PARAMETER_NAME, String.valueOf(number)));
    }

    private void 버스_즐겨찾기_삭제_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("delete-bus-like-success"));
    }

    private void 버스_즐겨찾기_삭제_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("delete-bus-like-fail"));
    }

    private ResultActions 버스_위치_최신화_요청(Long routeId) throws Exception {
        return mockMvc.perform(get("/api/v1/buses/arrive-at-bus-stop/" + ID)
                .contextPath("/api/v1"));
    }

    private void 버스_위치_최신화_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("arrive-at-bus-stop-success"));
    }
}

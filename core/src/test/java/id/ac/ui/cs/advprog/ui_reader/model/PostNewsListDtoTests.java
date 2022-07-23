package id.ac.ui.cs.advprog.ui_reader.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

import id.ac.ui.cs.advprog.ui_reader.model.constant.Env;
import id.ac.ui.cs.advprog.ui_reader.model.dto.base.ProtectedDto;
import id.ac.ui.cs.advprog.ui_reader.model.dto.PostNewsListDto;
import id.ac.ui.cs.advprog.ui_reader.util.exception.RequestErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PrepareForTest({ProtectedDto.class, Env.class})
@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
class PostNewsListDtoTests {

  @InjectMocks
  PostNewsListDto postNewsListDto;

  @Test
  void TestValidationError() {
    String apiKey = "definitelyNotAnApiKey";
    postNewsListDto.setApiKey(apiKey);
    assertThrows(RequestErrorException.class, () -> {
      postNewsListDto.validate();
    });
  }

  @Test
  void TestValidationOk() throws RequestErrorException {
    String apiKey = Env.APIKEY.val();
    postNewsListDto.setApiKey(apiKey);
    postNewsListDto.validate();
  }
}

package id.ac.ui.cs.advprog.ui_reader.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.powermock.api.mockito.PowerMockito.when;

import id.ac.ui.cs.advprog.ui_reader.model.User;
import id.ac.ui.cs.advprog.ui_reader.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@PrepareForTest(CustomUserDetailsService.class)
@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
class CustomUserDetailsServiceTests {
  @Spy
  @InjectMocks
  CustomUserDetailsService customUserDetailsService;

  @Mock
  UserRepository userRepository;

  @BeforeEach
  public void prepare() {
    customUserDetailsService = new CustomUserDetailsService(userRepository);
  }

  @Test
  void testLoadUserByUsernameNotExist() {
    String email = "bob@my.id";
    when(userRepository.findByEmail(email)).thenReturn(null);
    assertThrows(UsernameNotFoundException.class, () -> {
      customUserDetailsService.loadUserByUsername(email);
    });
  }

  @Test
  void testLoadUserByUsernameExist() {
    String email = "bob@my.id";
    User user = Whitebox.newInstance(User.class);
    user.setEmail(email);

    when(userRepository.findByEmail(email)).thenReturn(user);
    assertEquals(customUserDetailsService.loadUserByUsername(email).getUsername(), email);
  }
}

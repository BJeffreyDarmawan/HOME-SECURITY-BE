import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.enrico.dg.home.security.entity.constant.unit.test.CacheTestVariable;
import com.enrico.dg.home.security.service.impl.CacheServiceImpl;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class CacheServiceImplTest extends CacheTestVariable {

  @InjectMocks
  CacheServiceImpl cacheService;

  @Mock
  RedisTemplate redisTemplate;

  @Mock
  private ValueOperations<String, String> valueOperations;

  @Mock
  private ObjectMapper objectMapper;

  @Test
  public void findCacheByKeyTestSuccess() throws Exception {
    when(this.valueOperations.get(this.KEY)).thenReturn(this.VALUE);

    String value = this.cacheService.findCacheByKey(this.KEY, String.class);

    verify(this.redisTemplate).opsForValue();
    verify(this.valueOperations).get(this.KEY);

    assertEquals(this.VALUE, value);
  }

  @Test
  public void findCacheByKeyTestNoResult() throws Exception {
    when(this.valueOperations.get(this.KEY)).thenReturn(null);

    String value = this.cacheService.findCacheByKey(this.KEY, String.class);

    verify(this.redisTemplate).opsForValue();
    verify(this.valueOperations).get(this.KEY);

    assertEquals(null, value);
  }

  @Test
  public void findCacheByKeyTestException() throws Exception {
    when(this.redisTemplate.opsForValue()).thenThrow(new RuntimeException("Error"));

    String value = this.cacheService.findCacheByKey(this.KEY, String.class);

    verify(this.redisTemplate).opsForValue();

    assertEquals(null, value);
  }

  @Test
  public void createCacheTestSuccessWithNoExpired() throws Exception {
    Boolean status = this.cacheService.createCache(this.KEY, this.VALUE, 0);

    verify(this.redisTemplate).opsForValue();
    verify(this.valueOperations).set(this.KEY, this.VALUE);

    assertEquals(true, status);
  }

  @Test
  public void createCacheTestSuccessWithExpiryTime() throws Exception {
    Boolean status = this.cacheService.createCache(this.KEY, this.VALUE, this.EXPIRED_CACHE);

    verify(this.redisTemplate).opsForValue();
    verify(this.valueOperations).set(this.KEY, this.VALUE, this.EXPIRED_CACHE, TimeUnit.SECONDS);

    assertEquals(true, status);
  }

  @Test
  public void createCacheTestException() throws Exception {
    when(this.redisTemplate.opsForValue()).thenThrow(new RuntimeException("Error"));
    Boolean status = this.cacheService.createCache(this.KEY, this.VALUE, this.EXPIRED_CACHE);

    verify(this.redisTemplate).opsForValue();

    assertEquals(false, status);
  }

  @Test
  public void deleteCacheTestSuccess() throws Exception {
    Boolean status = this.cacheService.deleteCache(this.KEY);

    verify(this.redisTemplate).delete(this.KEY);

    assertEquals(true, status);
  }

  @Test
  public void deleteCacheTestException() throws Exception {
    doThrow(new RuntimeException("Error")).when(this.redisTemplate).delete(this.KEY);
    Boolean status = this.cacheService.deleteCache(this.KEY);

    verify(this.redisTemplate).delete(this.KEY);

    assertEquals(false, status);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    when(this.redisTemplate.opsForValue()).thenReturn(valueOperations);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.redisTemplate);
    verifyNoMoreInteractions(this.valueOperations);
  }
}

package mode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeSlotsTest {

  @Test
  public void getHourMarkClock08_MarkIs8() {
    assertEquals(8, TimeSlots.CLOCK_O8.getHourMark());
  }

  @Test
  public void getHourMarkClock09_MarkIs9() {
    assertEquals(9, TimeSlots.CLOCK_O9.getHourMark());
  }

  @Test
  public void getHourMarkClock10_MarkIs10() {
    assertEquals(10, TimeSlots.CLOCK_10.getHourMark());
  }

  @Test
  public void getHourMarkClock11_MarkIs11() {
    assertEquals(11, TimeSlots.CLOCK_11.getHourMark());
  }

  @Test
  public void getHourMarkClock12_MarkIs12() {
    assertEquals(12, TimeSlots.CLOCK_12.getHourMark());
  }

  @Test
  public void getHourMarkClock13_MarkIs13() {
    assertEquals(13, TimeSlots.CLOCK_13.getHourMark());
  }

  @Test
  public void getHourMarkClock14_MarkIs14() {
    assertEquals(14, TimeSlots.CLOCK_14.getHourMark());
  }

  @Test
  public void getHourMarkClock15_MarkIs15() {
    assertEquals(15, TimeSlots.CLOCK_15.getHourMark());
  }

  @Test
  public void getHourMarkClock16_MarkIs16() {
    assertEquals(16, TimeSlots.CLOCK_16.getHourMark());
  }

  @Test
  public void getHourMarkClock17_MarkIs17() {
    assertEquals(17, TimeSlots.CLOCK_17.getHourMark());
  }

}

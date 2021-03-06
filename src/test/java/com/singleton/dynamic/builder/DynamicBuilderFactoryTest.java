package com.singleton.dynamic.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for {@link DynamicBuilderFactory}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings("javadoc")
public class DynamicBuilderFactoryTest
{
    @Test
    public void testBuilderConstruction() throws Exception
    {
        DynamicBuilderFactory factory = new DynamicBuilderFactory();

        AppointmentBuilder builder = factory.createBuilderForClass(AppointmentBuilder.class);

        builder.appointmentId(2).display("Appt A");
        Appointment appointment = builder.build();

        assertEquals(2L, appointment.getAppointmentId());
        assertEquals("Appt A", appointment.getDisplay());
    }

    public interface AppointmentBuilder
    {
        AppointmentBuilder appointmentTypeId(long id);

        AppointmentBuilder appointmentId(long id);

        AppointmentBuilder display(String display);

        Appointment build();
    }

    public interface Appointment
    {
        long getAppointmentId();

        String getDisplay();

        long getAppointmentTypeId();
    }
}

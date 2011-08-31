package net.thucydides.maven.plugins;

import net.thucydides.core.reports.html.HtmlAggregateStoryReporter;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;


public class WhenGeneratingAnAggregateReport {

    ThucydidesAggregatorMojo plugin;

    @Mock
    File outputDirectory;

    @Mock
    File sourceDirectory;

    @Mock
    HtmlAggregateStoryReporter reporter;

    @Before
    public void setupPlugin() {

        MockitoAnnotations.initMocks(this);

        plugin = new ThucydidesAggregatorMojo();
        plugin.setOutputDirectory(outputDirectory);
        plugin.setSourceDirectory(sourceDirectory);
        plugin.setReporter(reporter);
    }

    @Test
    public void the_output_directory_can_be_set_via_the_plugin_configuration() throws Exception {

        plugin.execute();

        verify(reporter).setOutputDirectory(outputDirectory);

    }


    @Test
    public void the_aggregate_report_should_be_generated_using_the_specified_source_directory() throws Exception {

        plugin.execute();

        verify(reporter).generateReportsForStoriesFrom(sourceDirectory);
    }


    @Test
    public void the_aggregate_report_should_generate_a_new_output_directory_if_not_present() throws Exception {

        when(outputDirectory.exists()).thenReturn(false);

        plugin.execute();

        verify(outputDirectory).mkdirs();

    }

    @Test
    public void the_aggregate_report_should_use_an_existing_output_directory_if_present() throws Exception {

        when(outputDirectory.exists()).thenReturn(true);

        plugin.execute();

        verify(outputDirectory,never()).mkdirs();

    }


    @Test(expected = MojoExecutionException.class)
    public void if_the_report_cant_be_written_the_plugin_execution_should_fail() throws Exception {

        doThrow(new IOException("IO error")).when(reporter).generateReportsForStoriesFrom(sourceDirectory);

        plugin.execute();
    }

}

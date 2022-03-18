using HR.SSS.R3.Models;
using HR.SSS.R3.Processors;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Windows;

namespace HR.SSS.R3
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public static R3SessionContainer R3Session { get; set; }

        public MainWindow()
        {
            InitializeComponent();
            R3Session = new R3SessionContainer();
        }

        private void BtnGenerate_Click(object sender, RoutedEventArgs e)
        {
            if (this.SetupSessionValues())
            {
                R3ExcelFileProcessor.InitializeR3Records(R3Session);
            }

        }

        private bool SetupSessionValues()
        {
            if (String.IsNullOrEmpty(TxtInputFile.Text))
            {
                MessageBox.Show("Please specify your source Excel file.", "Missing Source File", 
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.InputFile = TxtInputFile.Text;
            }

            if (String.IsNullOrEmpty(TxtSheetName.Text))
            {
                MessageBox.Show("Please specify the name of the worksheet in your source Excel file.", "Unspecified Source Worksheet",
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.InputSheetName = TxtSheetName.Text;
            }

            if (String.IsNullOrEmpty(TxtOutputFileName.Text))
            {
                MessageBox.Show("Please specify the name of your output R3 file.", "Unspecified Output File Name", 
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.OutputFileName = TxtOutputFileName.Text;
            }

            if (String.IsNullOrEmpty(TxtEmployerName.Text))
            {
                MessageBox.Show("Please specify the Employer name.", "Unspecified Employer Name", 
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.EmployerName = TxtEmployerName.Text;
            }

            R3Session.IsHeaderPresent = ChkIsHeaderPresent.IsChecked.Value;
            R3Session.R3Records = new List<R3Record>();
            return true;
        }

        private void BtnBrowseInput_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "Excel Files|*.xls;*.xlsx;*.xlsm";

            if (openFileDialog.ShowDialog() == true)
            {
                R3Session.InputFile = openFileDialog.FileName;
                R3Session.InputDirectory = System.IO.Path.GetDirectoryName(openFileDialog.FileName);

                TxtInputFile.Text = openFileDialog.FileName;
            }
        }

        private void BtnClose_Click(object sender, RoutedEventArgs e)
        {
            App.Current.MainWindow.Close();
        }
    }
}

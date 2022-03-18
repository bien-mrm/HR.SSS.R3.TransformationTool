using HR.SSS.R3.Models;
using HR.SSS.R3.Processors;
using HR.SSS.R3.Utilities;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Windows;
using System.Windows.Media;

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
                R3ExcelFileProcessor.CaptureExcelRecords(R3Session);
                R3OutputFileProcessor.CreateOutputFile(R3Session);

                this.CompleteProcess();
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

            if (String.IsNullOrEmpty(TxtEmployerNumber.Text))
            {
                MessageBox.Show("Please specify the Employer Number.", "Unspecified Employer Number",
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.EmployerNumber = TxtEmployerNumber.Text;
            }


            if (String.IsNullOrEmpty(TxtApplicablePeriod.Text))
            {
                MessageBox.Show("Please specify the Applicable Period.", "Unspecified Applicable Period",
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.ApplicablePeriod = TxtApplicablePeriod.Text;
            }

            R3Session.IsHeaderPresent = ChkIsHeaderPresent.IsChecked.Value;
            R3Session.OutputFileName = RandomCodeGenerator.AssignCode(R3Session);
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

        private void OpenOutputFile()
        {
            var process = new Process();
            process.StartInfo = new ProcessStartInfo($"{ R3Session.InputDirectory }\\{ R3Session.OutputFileName }")
            {
                UseShellExecute = true
            };
            process.Start();
        }

        private void CompleteProcess()
        {
            double totalAmount = 0;

            foreach (var r3Record in R3Session.R3Records)
            {
                totalAmount += Convert.ToDouble(r3Record.SssContribution);
            }

            LblEmployeesCountLabel.Opacity = 100;
            LblEmployeesCount.Content = R3Session.R3Records.Count;

            LblTotalAmountLabel.Opacity = 100;
            LblTotalAmount.Content = String.Format("{0:n}", totalAmount);

            var bc = new BrushConverter();
            TxtOutputFileName.Background = (Brush)bc.ConvertFrom("#FF3BEA20");
            TxtOutputFileName.Text = R3Session.OutputFileName;

            this.OpenOutputFile();
        }
    }
}

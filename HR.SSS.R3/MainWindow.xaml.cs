using HR.SSS.R3.Models;
using HR.SSS.R3.Extractors;
using HR.SSS.R3.Utilities;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Windows;
using System.Windows.Media;
using System.Text.RegularExpressions;
using System.Windows.Input;
using HR.SSS.R3.Constants;

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

        private void BtnGenerateEmployeeList_Click(object sender, RoutedEventArgs e)
        {
            if (this.SetupSessionValues(OutputTypeConstants.R3EmployeeList))
            {
                R3ExcelFileExtractor.CaptureExcelRecords(R3Session);
                R3EmployeeListProcessor.CreateOutputFile(R3Session);

                this.CompleteProcess(OutputTypeConstants.R3EmployeeList);
            }
        }

        private void BtnGenerateR3Output_Click(object sender, RoutedEventArgs e)
        {
            if (this.SetupSessionValues(OutputTypeConstants.R3OutputFile))
            {
                R3ExcelFileExtractor.CaptureExcelRecords(R3Session);
                R3OutputFileProcessor.CreateOutputFile(R3Session);

                this.CompleteProcess(OutputTypeConstants.R3OutputFile);
            }
        }

        private bool SetupSessionValues(string type)
        {
            // Initialize again after the session has been flushed
            if (R3Session == null)
            {
                R3Session = new R3SessionContainer();
            }

            if (String.IsNullOrEmpty(TxtInputFile.Text))
            {
                MessageBox.Show("Please specify your source Excel file.", "Missing Source File", 
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.InputFile = TxtInputFile.Text;
                R3Session.InputDirectory = System.IO.Path.GetDirectoryName(TxtInputFile.Text);
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

            if (type == OutputTypeConstants.R3OutputFile && String.IsNullOrEmpty(TxtTransactionNumber.Text))
            {
                MessageBox.Show("Please specify the Transaction number.", "Unspecified Transaction Number",
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return false;
            }
            else
            {
                R3Session.TransactionNumber = TxtTransactionNumber.Text;
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
            R3Session.OutputFileName = OutputFileNameGenerator.AssignCode(R3Session);
            R3Session.R3Records = new List<R3Record>();

            return true;
        }

        private void BtnBrowseInput_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "Excel Files|*.xls;*.xlsx;*.xlsm";

            if (openFileDialog.ShowDialog() == true)
            {
                TxtInputFile.Text = openFileDialog.FileName;
            }
        }

        private void BtnClose_Click(object sender, RoutedEventArgs e)
        {
            App.Current.MainWindow.Close();
        }

        private void NumberValidationTextBox(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }

        private void TextBoxPasting(object sender, DataObjectPastingEventArgs e)
        {
            if (e.DataObject.GetDataPresent(typeof(String)))
            {
                String text = (String)e.DataObject.GetData(typeof(String));
                if (!IsTextAllowed(text))
                {
                    e.CancelCommand();
                }
            }
            else
            {
                e.CancelCommand();
            }
        }
        private static bool IsTextAllowed(string text)
        {
            Regex _regex = new Regex("[^0-9.-]+");
            return !_regex.IsMatch(text);
        }

        private void OpenOutputFile(string filePath)
        {
            var process = new Process();

            process.StartInfo = new ProcessStartInfo(filePath)
            {
                UseShellExecute = true
            };

            process.Start();
        }

        private void CompleteProcess(string type)
        {
            LblEmployeesCountLabel.Opacity = 100;
            LblEmployeesCount.Content = R3Session.R3Records.Count;

            LblTotalAmountLabel.Opacity = 100;
            LblTotalAmount.Content = $"₱{ String.Format("{0:n}", R3Session.TotalAmount) }";

            var bc = new BrushConverter();
            TxtOutputFileName.Background = (Brush) bc.ConvertFrom("#FFA8FFB0");

            string outputFilePath = $"{ R3Session.InputDirectory }\\{ type }\\{ R3Session.OutputFileName }";
            TxtOutputFileName.Text = outputFilePath;

            this.OpenOutputFile(outputFilePath);

            // Flush the session data
            R3Session = null;
        }
    }
}

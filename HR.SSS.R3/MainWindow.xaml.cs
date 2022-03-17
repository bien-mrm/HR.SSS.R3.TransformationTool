using HR.SSS.R3.Models;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace HR.SSS.R3
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private static R3SessionContainer R3Session { get; set; }

        public MainWindow()
        {
            InitializeComponent();
            R3Session = new R3SessionContainer();
        }

        private void BtnGenerate_Click(object sender, RoutedEventArgs e)
        {
            this.SetupSessionValues();


        }

        private void SetupSessionValues()
        {
            if (String.IsNullOrEmpty(TxtInputFile.Text))
            {
                MessageBox.Show("Please specify your source Excel file.", "Missing Source File");
                return;
            }
            else
            {
                R3Session.InputFile = TxtInputFile.Text;
            }

            if (String.IsNullOrEmpty(TxtOutputFileName.Text))
            {
                MessageBox.Show("Please specify the name of your output R3 file.", "Unspecified Output File Name");
                return;
            }
            else
            {
                R3Session.OutputFileName = TxtOutputFileName.Text;
            }

            if (String.IsNullOrEmpty(TxtEmployerName.Text))
            {
                MessageBox.Show("Please specify the Employer name.", "Unspecified Employer Name");
                return;
            }
            else
            {
                R3Session.EmployerName = TxtEmployerName.Text;
            }

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
    }
}

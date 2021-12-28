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
using Excel = Microsoft.Office.Interop.Excel;

namespace Lab2
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        Random x = new Random();
        Random y = new Random();
        public Point p = new Point();
        public Point[] pi = new Point[2];
        int Status_exp = 0;
        DateTime Start;
        DateTime Stop;
        TimeSpan Elapsed = new TimeSpan();
        public Point s;






        object misValue = System.Reflection.Missing.Value;
        List<List<string>> results = new List<List<string>>();
        List<string> row = new List<string>();

        public MainWindow()
        {
            InitializeComponent();
        }

        public void expir()
        {
            int[] rez_arr = new int[2];
            Array.Clear(pi, 0, pi.Length - 1);
            Start = new DateTime(0);
            icDrow.Children.Clear();
            icDrow.Strokes.Clear();
            for (int i = 0; i < 2; i++)
            {
                pi[i] = DrawObject(p);
            }
            Status_exp = Status_exp + 1;
        }

        private void ButtonStart_Click(object sender, RoutedEventArgs e)
        {
            lbRez.Items.Clear();
            lbRez.Items.Add("Результати");
            expir();
        }
        private Point DrawObject(Point p)
        {
            Ellipse el = new Ellipse();
            el.Width = 10;
            el.Height = 10;
            p.X = x.Next(Convert.ToInt32(this.Width - el.Width));
            p.Y = y.Next(Convert.ToInt32(icDrow.ActualHeight - el.Height));
            el.Fill = Brushes.Red;
            InkCanvas.SetLeft(el, p.X);
            InkCanvas.SetTop(el, p.Y);
            icDrow.Children.Add(el);
            return p;
        }
        private void icUp(object sender, MouseButtonEventArgs e)
        {
            if (Status_exp > 0)
            {
                Point[] n = Array.FindAll(pi, element => (Math.Abs(element.X - e.GetPosition(this).X) < 20) &&
                (Math.Abs(element.Y - e.GetPosition(this).Y) < 20));
                if (Array.Exists(pi, element => (Math.Abs(element.X - e.GetPosition(this).X) < 20) &&
                (Math.Abs(element.Y - e.GetPosition(this).Y) < 20)))
                {
                    if (!((Math.Abs(s.X - e.GetPosition(this).X) < 10) && (Math.Abs(s.Y - e.GetPosition(this).Y) < 10)))
                    {
                        s = e.GetPosition(this);
                        if (Start.Ticks == 0)
                        {
                            Start = DateTime.Now;
                        }
                        else
                        {
                            Stop = DateTime.Now;
                            Elapsed = Stop.Subtract(Start);
                            long elapsedTicks = Stop.Ticks - Start.Ticks;
                            TimeSpan elapsedSpan = new TimeSpan(elapsedTicks);
                            double rez = Math.Sqrt(Math.Pow(pi[0].X - pi[1].X, 2) + Math.Pow(pi[0].Y - pi[1].Y, 2));
                            lbRez.Items.Add("Експеримент " + Status_exp.ToString() + ", Час: " + Elapsed.Milliseconds.ToString()
                                + " Відстань: " + rez.ToString("N3"));
                            row = new List<string>();
                            row.Add(String.Format("{0}", Status_exp.ToString()));
                            row.Add(String.Format("{0}", elapsedSpan.Milliseconds.ToString()));
                            row.Add(String.Format("{0}", Math.Round(rez)));
                            results.Add(row);
                            if (Status_exp < 100)
                                expir();
                        }
                    }
                }
            }
        }

        private void Window_Closed(object sender, EventArgs e)
        {
            Excel.Application ex = new Excel.Application();
            Excel.Workbook workBook = ex.Workbooks.Add(System.Reflection.Missing.Value);
            Excel.Worksheet sheet = (Excel.Worksheet)ex.Worksheets.get_Item(1);
            sheet.Name = "Lab";
            sheet.Cells[1, 1] = "Експеримент";
            sheet.Cells[1, 2] = "Час";
            sheet.Cells[1, 3] = "Відстань";
            for (int i = 2; i <= Status_exp; i++)
            {
                sheet.Cells[i, 1] = results[i - 2][0];
                sheet.Cells[i, 2] = results[i - 2][1];
                sheet.Cells[i, 3] = results[i - 2][2];
            }
            ex.ActiveWorkbook.SaveAs("D:\\VS\\ЛМІ\\Lab2\\lab_individual.xlsx", Excel.XlFileFormat.xlOpenXMLWorkbook, misValue, misValue, misValue, misValue, Excel.XlSaveAsAccessMode.xlShared, misValue, misValue, misValue, misValue, misValue);
        }
    }
}
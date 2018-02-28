using System;
using System.Globalization;
using System.Windows;

namespace ChubbyLapse
{
    /// <inheritdoc cref="MainWindow" />
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void IntervalSelectorValueChanged(object sender, RoutedPropertyChangedEventArgs<double> eventArgs)
        {
            if (IntervalUpdateLabel == null) return;
            IntervalSelector.Value = Convert.ToDouble(IntervalSelector.Value.ToString("F1"));
            IntervalUpdateLabel.Content = IntervalSelector.Value;
        }
    }
}

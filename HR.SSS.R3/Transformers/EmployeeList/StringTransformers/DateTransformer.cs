using HR.SSS.R3.Extractors.Interfaces;
using System;

namespace HR.SSS.R3.Extractors.EmployeeList.StringTransformers
{
    public class DateTransformer : IStringTransformable
    {
        public string TransformString()
        {
            return $"Date: { DateTime.Now.ToString("MMM dd, yyyy") }";
        }
    }
}

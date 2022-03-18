using HR.SSS.R3.Processors.Interfaces;
using System;

namespace HR.SSS.R3.Processors.StringTransformers
{
    public class DateTransformer : IStringTransformable
    {
        public string TransformString()
        {
            return $"Date: { DateTime.Now.ToString("MMM dd, yyyy") }";
        }
    }
}

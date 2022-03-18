using HR.SSS.R3.Processors.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HR.SSS.R3.Processors.StringTransformers
{
    public class DateTransformer : IStringTransformer
    {
        public string TransformString()
        {
            return $"Date: { DateTime.Now.ToString("MMddyy") }"
        }
    }
}

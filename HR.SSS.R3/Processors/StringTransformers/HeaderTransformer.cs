using HR.SSS.R3.Processors.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HR.SSS.R3.Processors.StringTransformers
{
    public class HeaderTransformer : IStringTransformable
    {
        public string TransformString()
        {
            return "FAMILY NAME     GIVEN NAME      MI  SS NUMBER       S.S.    E.C.   RMRK  DTHRD";
        }
    }
}

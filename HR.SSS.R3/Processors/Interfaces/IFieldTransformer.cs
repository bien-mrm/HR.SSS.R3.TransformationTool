using HR.SSS.R3.Models;
using System.IO;

namespace HR.SSS.R3.Processors.Interfaces
{
    interface IFieldTransformer
    {
        string TransformField(R3SessionContainer r3Session);
    }
}

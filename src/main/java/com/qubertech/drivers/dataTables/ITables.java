package com.qubertech.drivers.dataTables;

import com.qubertech.drivers.exception.ExcelTableLoadFailure;
import java.util.List;

public interface ITables<T>
{
    List<T> getTableData();
    void setTableData(List<T> data);
    void loadTable() throws ExcelTableLoadFailure;
    
}

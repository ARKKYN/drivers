package com.qubertech.drivers.dataMapper;

import com.qubertech.drivers.exception.ExcelTableLoadFailure;
import java.util.List;

public interface IDataMapper<T>
{
  List<T> mapData() throws ExcelTableLoadFailure;
}

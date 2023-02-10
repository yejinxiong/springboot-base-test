package com.cactus.springboot.mysql.batch.controller;

import com.cactus.springboot.mysql.batch.entity.QmItems;
import com.cactus.springboot.mysql.batch.service.BatchInsertService;
import com.cactus.springboot.mysql.batch.utils.DataUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;


/**
 * <table width="650" border="1" cellspacing="0">
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47; height: 30px;" colspan="6">MySQL批量插入</th>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">方案</th>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">数据量（500）</th>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">数据量（1000）</th>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">数据量（10000）</th>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">数据量（50000）</th>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">数据量（100000）</th>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">单条sql-自动提交事务</th>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.0768391秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.150596秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">1.6266086秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">7.5788613秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">14.8247183秒</td>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">单条sql-手动提交事务</th>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.0699601秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.1489763秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">1.6083319秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">7.5456994秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">14.6321591秒</td>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">sql拼接-自动提交事务</th>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.0275702秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.056821秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.4384623秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">2.4424397秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">4.8848715秒</td>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; background-color: #70AD47; color: #FFF;">sql拼接-手动提交事务</th>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.0244632秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.043885秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.4736628秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">2.3531497秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">5.0792645秒</td>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">list分片-sql拼接-自动提交事务</th>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.0202661秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.0408196秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">0.4360807秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">2.0672219秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #C5E0B3;">4.2039976秒</td>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47;">list分片-sql拼接-手动提交事务</th>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.02569秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.043745秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">0.2733782秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">1.2847148秒</td>
 *         <td style="text-align: center; border: 1px solid #FFF; color: #000; background-color: #E2EFD9;">2.5902296秒</td>
 *     </tr>
 *     <tr>
 *         <th style="text-align: center; border: 1px solid #FFF; color: #FFF; background-color: #70AD47; height: 30px;">结论</th>
 *         <td style="text-align: left; border: 1px solid #FFF; color: #000; background-color: #C5E0B3; height: 30px;" colspan="5">
 *             1.数据量不是非常大（10000条）时，可以使用sql拼接的方式；<br/>
 *             2.数据量很大时，可以使用集合分片之后由xml进行sql的方式。
 *         </td>
 *     </tr>
 * </table>
 */

@RestController
@RequestMapping("/insert")
public class BatchInsertController {

    @Resource
    private BatchInsertService batchInsertService;

    /**
     * 批量插入：单条sql-自动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/forSingle")
    public String forSingle(int size) {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.forSingle(list);
    }

    /**
     * 批量插入：单条sql-手动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/forSingleManualCommit")
    public String forSingleManualCommit(int size) throws SQLException {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.forSingleManualCommit(list);
    }

    /**
     * 批量插入：sql拼接-自动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/sqlForeach")
    public String sqlForeach(int size) {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.sqlForeach(list);
    }

    /**
     * 批量插入：sql拼接-手动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/sqlForeachManualCommit")
    public String sqlForeachManualCommit(int size) throws SQLException {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.sqlForeachManualCommit(list);
    }

    /**
     * 批量插入：list分片-sql拼接-自动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/splitListSqlForeach")
    public String splitListSqlForeach(int size) {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.splitListSqlForeach(list);
    }

    /**
     * 批量插入：list分片-sql拼接-手动提交事务
     *
     * @param size 数据量
     * @return 操作信息
     */
    @GetMapping("/splitListSqlForeachManualCommit")
    public String splitListSqlForeachManualCommit(int size) throws SQLException {
        List<QmItems> list = DataUtil.createList(size, 1000);
        return batchInsertService.splitListSqlForeachManualCommit(list);
    }

}

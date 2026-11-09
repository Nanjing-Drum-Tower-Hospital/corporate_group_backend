-- ============================================================
-- 更新内容：
-- 1. 商品字典表新增 category 字段（商品分类）
-- 2. 给历史商品分类打标：1=耳蜗，2=耳膜，0=其他/尾差调整
-- 3. 尾差调整拆分：原尾差调整归为耳蜗，新增耳膜尾差调整
-- 4. 新增系统用户 lh053，默认密码 123456Aa!
-- ============================================================

-- ------------------------------------------------------------
-- 步骤1：商品字典表新增 category 字段
-- ------------------------------------------------------------
ALTER TABLE dbo.item_dictionary
ADD category INT DEFAULT 0;
GO

-- ------------------------------------------------------------
-- 步骤2：给历史商品分类打标
-- 说明：
--   1 = 耳蜗
--   2 = 耳膜
--   0 = 其他/尾差调整
--   请根据实际情况调整 WHERE 条件
-- ------------------------------------------------------------

-- 耳蜗商品
UPDATE dbo.item_dictionary
SET category = 1
WHERE name LIKE '%耳蜗%'
  AND name NOT LIKE '%尾差调整%';
GO

-- 耳膜商品
UPDATE dbo.item_dictionary
SET category = 2
WHERE name LIKE '%耳膜%'
  AND name NOT LIKE '%尾差调整%';
GO

-- 尾差调整商品先标记为其他（后续步骤再细分）
UPDATE dbo.item_dictionary
SET category = 0
WHERE name LIKE '%尾差调整%';
GO

-- ------------------------------------------------------------
-- 步骤3：处理尾差调整商品
-- 说明：
--   原来的尾差调整商品归为耳蜗尾差调整（category=1）
--   新增一个耳膜尾差调整商品（category=2）
-- ------------------------------------------------------------

-- 3.1 将现有尾差调整商品改为耳蜗尾差调整
-- 注意：如果存在多个尾差调整商品，请根据实际 id/code 调整 WHERE 条件
UPDATE dbo.item_dictionary
SET name = '耳蜗尾差调整',
    category = 1
WHERE name LIKE '%尾差调整%';
GO

-- 3.2 新增耳膜尾差调整商品
-- 注意：如果 manufacturer_dictionary 中没有“尾差调整”制造商，请把 manufacturer_id 改成实际的制造商 id
INSERT INTO dbo.item_dictionary (
    code,
    name,
    model,
    unit_name,
    unit_price_excluding_tax,
    manufacturer_id,
    bill_item,
    standards,
    approval_no,
    type,
    category,
    expire_date,
    create_date,
    extend_code1,
    extend_code2,
    extend_code3,
    extend_code4,
    extend_code5,
    comment1,
    comment2,
    comment3,
    comment4,
    comment5,
    certification_url,
    pinyin_code,
    retail_price,
    retail_employee_price
)
VALUES (
    'WCDZ_EM',
    '耳膜尾差调整',
    '耳膜尾差调整',
    '个',
    0.01,
    (SELECT id FROM manufacturer_dictionary WHERE manufacturer_name LIKE '%尾差调整%'),
    NULL,
    NULL,
    NULL,
    '耳膜尾差调整',
    2,
    NULL,
    GETDATE(),
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    'EMWCDZ',
    0,
    0
);
GO

-- ------------------------------------------------------------
-- 步骤4：新增系统用户 lh053
-- 说明：默认密码 123456Aa!，加密后的值为 W7jS6lbxN
-- ------------------------------------------------------------
INSERT INTO dbo.user_list (username, password, full_name)
VALUES ('lh053', 'W7jS6lbxN', 'lh053');
GO

-- ------------------------------------------------------------
-- 验证 SQL
-- ------------------------------------------------------------
-- SELECT category, COUNT(*) AS cnt FROM dbo.item_dictionary GROUP BY category;
-- SELECT * FROM dbo.user_list WHERE username = 'lh053';

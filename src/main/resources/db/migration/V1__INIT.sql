-- CreateTable
CREATE TABLE "accounts"
(
    "id"        UUID NOT NULL,
    "type"      TEXT,
    "provider"  TEXT,
    "scope"     TEXT,
    "createdAt" timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamp(6) with time zone,
    "userId"    UUID,

    CONSTRAINT "accounts_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "users"
(
    "id"            UUID NOT NULL,
    "name"          TEXT,
    "email"         TEXT,
    "emailVerified" timestamp(6) with time zone,
    "image"         TEXT,
    "password"      TEXT,
    "createdAt"     timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt"     timestamp(6) with time zone,

    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "roles"
(
    "id"          SERIAL NOT NULL,
    "name"        TEXT,
    "description" TEXT,
    "permission"  TEXT[],
    "userId"      UUID   NOT NULL,

    CONSTRAINT "roles_pkey" PRIMARY KEY ("id", "userId")
);

-- CreateTable
CREATE TABLE "address"
(
    "id"       BIGSERIAL NOT NULL,
    "city"     TEXT,
    "district" TEXT,
    "wards"    TEXT,
    "userId"   UUID,

    CONSTRAINT "address_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "favorites"
(
    "createdAt" timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "deletedAt" timestamp(6) with time zone,
    "userId"    UUID   NOT NULL,
    "bookId"    BIGINT NOT NULL,

    CONSTRAINT "favorites_pkey" PRIMARY KEY ("userId", "bookId")
);

-- CreateTable
CREATE TABLE "reviews"
(
    "rating"    INTEGER,
    "content"   TEXT,
    "createdAt" timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamp(6) with time zone,
    "userId"    UUID   NOT NULL,
    "bookId"    BIGINT NOT NULL,

    CONSTRAINT "reviews_pkey" PRIMARY KEY ("userId", "bookId")
);

-- CreateTable
CREATE TABLE "books"
(
    "id"           BIGSERIAL NOT NULL,
    "name"         TEXT,
    "price"        MONEY,
    "discount"     INTEGER                     DEFAULT 0,
    "episode"      TEXT,
    "gallery"      TEXT[],
    "availableAge" TEXT,
    "supplier"     TEXT,
    "authors"      TEXT[],
    "translator"   TEXT,
    "publisher"    TEXT,
    "publishYear"  TEXT,
    "language"     TEXT,
    "size"         TEXT,
    "numsPage"     INTEGER,
    "content"      TEXT,
    "weight"       TEXT,
    "bookLayout"   TEXT,
    "createdAt"    timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt"    timestamp(6) with time zone,
    "skuCode"      TEXT,
    "categoryId"   BIGINT,
    "seriesId"     BIGINT,

    CONSTRAINT "books_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "inventory"
(
    "skuCode"   TEXT NOT NULL,
    "quantity"  INTEGER,
    "status"    TEXT,
    "createdAt" timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamp(6) with time zone,

    CONSTRAINT "inventory_pkey" PRIMARY KEY ("skuCode")
);

-- CreateTable
CREATE TABLE "genres"
(
    "id"          BIGSERIAL NOT NULL,
    "name"        TEXT,
    "description" TEXT,
    "createdAt"   timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt"   timestamp(6) with time zone,
    "bookId"      BIGINT,

    CONSTRAINT "genres_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "categories"
(
    "id"          BIGSERIAL NOT NULL,
    "name"        TEXT,
    "subCategory" TEXT,
    "description" TEXT,
    "createdAt"   timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt"   timestamp(6) with time zone,

    CONSTRAINT "categories_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "series"
(
    "id"            BIGSERIAL NOT NULL,
    "name"          TEXT,
    "subscribes"    BIGINT,
    "newestEpisode" INT,
    "createdAt"     timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    "updatedAt"     timestamp(6) with time zone,

    CONSTRAINT "series_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "orders"
(
    "id"       UUID NOT NULL,
    "quantity" INTEGER DEFAULT 0,
    "amount"   MONEY,
    "userId"   UUID,

    CONSTRAINT "orders_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "order_items"
(
    "id"       UUID NOT NULL,
    "quantity" INTEGER DEFAULT 0,
    "bookId"   BIGINT,
    "orderId"  UUID,

    CONSTRAINT "order_items_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "users_email_key" ON "users" ("email");

-- CreateIndex
CREATE UNIQUE INDEX "books_skuCode_key" ON "books" ("skuCode");

-- CreateIndex
CREATE UNIQUE INDEX "orders_id_key" ON "orders" ("id");

-- AddForeignKey
ALTER TABLE "accounts"
    ADD CONSTRAINT "accounts_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "roles"
    ADD CONSTRAINT "roles_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "address"
    ADD CONSTRAINT "address_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "favorites"
    ADD CONSTRAINT "favorites_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "favorites"
    ADD CONSTRAINT "favorites_bookId_fkey" FOREIGN KEY ("bookId") REFERENCES "books" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "reviews"
    ADD CONSTRAINT "reviews_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "reviews"
    ADD CONSTRAINT "reviews_bookId_fkey" FOREIGN KEY ("bookId") REFERENCES "books" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "books"
    ADD CONSTRAINT "books_skuCode_fkey" FOREIGN KEY ("skuCode") REFERENCES "inventory" ("skuCode") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "books"
    ADD CONSTRAINT "books_categoryId_fkey" FOREIGN KEY ("categoryId") REFERENCES "categories" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "books"
    ADD CONSTRAINT "books_seriesId_fkey" FOREIGN KEY ("seriesId") REFERENCES "series" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "genres"
    ADD CONSTRAINT "genres_bookId_fkey" FOREIGN KEY ("bookId") REFERENCES "books" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "orders"
    ADD CONSTRAINT "orders_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "order_items"
    ADD CONSTRAINT "order_items_bookId_fkey" FOREIGN KEY ("bookId") REFERENCES "books" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "order_items"
    ADD CONSTRAINT "order_items_orderId_fkey" FOREIGN KEY ("orderId") REFERENCES "orders" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

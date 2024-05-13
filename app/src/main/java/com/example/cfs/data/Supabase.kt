package com.example.cfs.data

import android.net.http.HttpResponseCache.install
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

private val supabaseUrl: String = System.getenv("SUPABASE_URL") ?: "fire"
private val supabaseKey: String = System.getenv("SUPABASE_KEY") ?: "hot"

val supabase = createSupabaseClient(
    supabaseUrl, supabaseKey
) {
    install(Postgrest)
}



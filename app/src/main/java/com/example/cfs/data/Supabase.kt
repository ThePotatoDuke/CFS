package com.example.cfs.data

import com.example.cfs.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

private val supabaseUrl: String = BuildConfig.SUPABASE_URL
private val supabaseKey: String = BuildConfig.SUPABASE_KEY

val supabase = createSupabaseClient(
    supabaseUrl, supabaseKey
) {
    install(Postgrest)
}


